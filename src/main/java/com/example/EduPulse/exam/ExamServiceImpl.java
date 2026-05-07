package com.example.EduPulse.exam;


import com.example.EduPulse.exam.examType.ExamType;
import com.example.EduPulse.exam.examType.ExamTypeRepo;
import com.example.EduPulse.exception.ExamException;
import com.example.EduPulse.section.Section;
import com.example.EduPulse.section.SectionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class ExamServiceImpl implements ExamService {

    private final ExamRepo     examRepository;
    private final SectionRepo sectionRepository;
    private final ExamTypeRepo examTypeRepo;
    private final ExamMapper         mapper;

    @Override @Transactional
    public ExamDto.Response create(ExamDto.CreateRequest req) {
        validateDates(req.startDate(), req.endDate());

        if (examRepository.existsByExamTypeIdAndSectionIdAndAcademicYear(
                req.examTypeId(), req.sectionId(), req.academicYear()))
            throw new ExamException.DuplicateExam(req.sectionId().toString(), req.academicYear());

        ExamType examType = examTypeRepo.findById(req.examTypeId())
                .orElseThrow(() -> new RuntimeException("ExamType not found: " + req.examTypeId()));

        Section section = sectionRepository.findById(req.sectionId())
                .orElseThrow(() -> new RuntimeException("Section not found: " + req.sectionId()));

        Exam exam = Exam.builder()
                .examType(examType)
                .section(section)
                .academicYear(req.academicYear())
                .startDate(req.startDate())
                .endDate(req.endDate())
                .build();

        return mapper.toDto(examRepository.save(exam));
    }

    @Override @Transactional
    public ExamDto.Response update(UUID id, ExamDto.UpdateRequest req) {
        Exam exam = findOrThrow(id);

        LocalDate newStart = req.startDate() != null ? req.startDate() : exam.getStartDate();
        LocalDate newEnd   = req.endDate()   != null ? req.endDate()   : exam.getEndDate();
        validateDates(newStart, newEnd);

        if (req.examTypeId() != null) {
            ExamType examType = examTypeRepo.findById(req.examTypeId())
                    .orElseThrow(() -> new RuntimeException("ExamType not found: " + req.examTypeId()));
            exam.setExamType(examType);
        }

        String newYear = req.academicYear() != null ? req.academicYear() : exam.getAcademicYear();

        if (examRepository.existsByExamTypeIdAndSectionIdAndAcademicYearAndIdNot(
                exam.getExamType().getId(), exam.getSection().getId(), newYear, id))
            throw new ExamException.DuplicateExam(exam.getSection().getId().toString(), newYear);

        if (req.academicYear() != null) exam.setAcademicYear(req.academicYear());
        if (req.startDate()    != null) exam.setStartDate(req.startDate());
        if (req.endDate()      != null) exam.setEndDate(req.endDate());

        return mapper.toDto(examRepository.save(exam));
    }

    @Override @Transactional(readOnly = true)
    public ExamDto.Response getById(UUID id) {
        return mapper.toDto(findOrThrow(id));
    }

    @Override @Transactional(readOnly = true)
    public List<ExamDto.Response> getBySection(UUID sectionId) {
        return mapper.toDtoList(examRepository.findBySectionIdOrderByStartDateAsc(sectionId));
    }

    @Override @Transactional(readOnly = true)
    public List<ExamDto.Response> getBySectionAndYear(UUID sectionId, String academicYear) {
        return mapper.toDtoList(
                examRepository.findBySectionIdAndAcademicYear(sectionId, academicYear));
    }

    @Override @Transactional(readOnly = true)
    public List<ExamDto.Response> getByExamType(UUID examTypeId) {
        return mapper.toDtoList(examRepository.findByExamTypeId(examTypeId));
    }

    @Override @Transactional
    public void delete(UUID id) {
        examRepository.delete(findOrThrow(id));
    }

    private Exam findOrThrow(UUID id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ExamException.NotFound(id.toString()));
    }

    private void validateDates(LocalDate start, LocalDate end) {
        if (start != null && end != null && end.isBefore(start))
            throw new ExamException.InvalidDateRange();
    }
}