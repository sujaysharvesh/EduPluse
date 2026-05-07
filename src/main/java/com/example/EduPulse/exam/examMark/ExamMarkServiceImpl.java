package com.example.EduPulse.exam.examMark;

import com.example.EduPulse.exam.Exam;
import com.example.EduPulse.exam.ExamRepo;
import com.example.EduPulse.exception.ExamMarkException;
import com.example.EduPulse.subject.Subject;
import com.example.EduPulse.subject.SubjectRepo;
import com.example.EduPulse.user.student.Student;
import com.example.EduPulse.user.student.StudentRepo;
import com.example.EduPulse.user.teacher.Teacher;
import com.example.EduPulse.user.teacher.TeacherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ExamMarkServiceImpl implements ExamMarkService {

        private final ExamMarkRepo examMarkRepository;
        private final ExamRepo examRepository;
        private final StudentRepo studentRepository;
        private final SubjectRepo subjectRepository;
        private final TeacherRepo teacherRepository;
        private final ExamMarkMapper     mapper;

        @Override @Transactional
        public ExamMarkDto.Response create(ExamMarkDto.CreateRequest req) {
            Exam exam    = findExamOrThrow(req.examId());
            Student student = findStudentOrThrow(req.studentId());
            Subject subject = findSubjectOrThrow(req.subjectId());

            if (examMarkRepository.existsByStudentIdAndExamIdAndSubjectId(
                    req.studentId(), req.examId(), req.subjectId()))
                throw new ExamMarkException.AlreadyEntered(
                        req.studentId().toString(), req.subjectId().toString());

            validateMark(req.obtainedMark(), req.isAbsent(), exam.getExamType().getMaxMark());

            Teacher teacher = req.enteredBy() != null
                    ? teacherRepository.findById(req.enteredBy()).orElse(null)
                    : null;

            ExamMark mark = ExamMark.builder()
                    .student(student)
                    .exam(exam)
                    .subject(subject)
                    .obtainedMark(req.obtainedMark())
                    .grade(req.grade())
                    .isAbsent(req.isAbsent() != null ? req.isAbsent() : false)
                    .enteredBy(teacher)
                    .build();

            return mapper.toDto(examMarkRepository.save(mark));
        }

        @Override @Transactional
        public List<ExamMarkDto.Response> bulkCreate(ExamMarkDto.BulkCreateRequest req) {
            Exam    exam    = findExamOrThrow(req.examId());
            Student student = findStudentOrThrow(req.studentId());
            Teacher teacher = teacherRepository.findById(req.enteredBy())
                    .orElseThrow(() -> new RuntimeException("Teacher not found: " + req.enteredBy()));

            List<ExamMark> marks = req.marks().stream().map(entry -> {
                Subject subject = findSubjectOrThrow(entry.subjectId());

                if (examMarkRepository.existsByStudentIdAndExamIdAndSubjectId(
                        req.studentId(), req.examId(), entry.subjectId()))
                    throw new ExamMarkException.AlreadyEntered(
                            req.studentId().toString(), entry.subjectId().toString());

                validateMark(entry.obtainedMark(), entry.isAbsent(), exam.getExamType().getMaxMark());

                return ExamMark.builder()
                        .student(student)
                        .exam(exam)
                        .subject(subject)
                        .obtainedMark(entry.obtainedMark())
                        .grade(entry.grade())
                        .isAbsent(entry.isAbsent() != null ? entry.isAbsent() : false)
                        .enteredBy(teacher)
                        .build();
            }).toList();

            return mapper.toDtoList(examMarkRepository.saveAll(marks));
        }

        @Override @Transactional
        public ExamMarkDto.Response update(UUID id, ExamMarkDto.UpdateRequest req) {
            ExamMark mark = findOrThrow(id);

            Integer newMark = req.obtainedMark() != null ? req.obtainedMark() : mark.getObtainedMark();
            Boolean absent  = req.isAbsent()     != null ? req.isAbsent()     : mark.getIsAbsent();
            validateMark(newMark, absent, mark.getExam().getExamType().getMaxMark());

            if (req.obtainedMark() != null) mark.setObtainedMark(req.obtainedMark());
            if (req.grade()        != null) mark.setGrade(req.grade());
            if (req.isAbsent()     != null) mark.setIsAbsent(req.isAbsent());

            return mapper.toDto(examMarkRepository.save(mark));
        }

        @Override @Transactional(readOnly = true)
        public ExamMarkDto.Response getById(UUID id) {
            return mapper.toDto(findOrThrow(id));
        }

        @Override @Transactional(readOnly = true)
        public List<ExamMarkDto.Response> getByStudent(UUID studentId) {
            return mapper.toDtoList(examMarkRepository.findByStudentIdOrderByExamStartDateAsc(studentId));
        }

        @Override @Transactional(readOnly = true)
        public List<ExamMarkDto.Response> getByStudentAndExam(UUID studentId, UUID examId) {
            return mapper.toDtoList(examMarkRepository.findByStudentIdAndExamId(studentId, examId));
        }

        @Override @Transactional(readOnly = true)
        public List<ExamMarkDto.Response> getByExam(UUID examId) {
            return mapper.toDtoList(examMarkRepository.findByExamId(examId));
        }

        @Override @Transactional(readOnly = true)
        public List<ExamMarkDto.Response> getByExamAndSubject(UUID examId, UUID subjectId) {
            return mapper.toDtoList(examMarkRepository.findByExamIdAndSubjectId(examId, subjectId));
        }

        @Override @Transactional(readOnly = true)
        public List<ExamMarkDto.Response> getReportCard(
                UUID studentId, UUID sectionId, String academicYear) {
            return mapper.toDtoList(
                    examMarkRepository.findReportCard(studentId, sectionId, academicYear));
        }

        @Override @Transactional
        public void delete(UUID id) {
            examMarkRepository.delete(findOrThrow(id));
        }

        // ── Helpers ───────────────────────────────

        private ExamMark findOrThrow(UUID id) {
            return examMarkRepository.findById(id)
                    .orElseThrow(() -> new ExamMarkException.NotFound(id.toString()));
        }

        private Exam findExamOrThrow(UUID id) {
            return examRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Exam not found: " + id));
        }

        private Student findStudentOrThrow(UUID id) {
            return studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found: " + id));
        }

        private Subject findSubjectOrThrow(UUID id) {
            return subjectRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Subject not found: " + id));
        }

        private void validateMark(Integer obtained, Boolean isAbsent, int maxMark) {
            if (isAbsent == Boolean.TRUE || obtained == null) return;
            if (obtained > maxMark)
                throw new ExamMarkException.ExceedsMaxMark(obtained, maxMark);
        }

}
