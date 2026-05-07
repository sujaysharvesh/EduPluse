package com.example.EduPulse.exam.examType;

import com.example.EduPulse.exception.ExamTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
class ExamTypeServiceImpl implements ExamTypeService {

    private final ExamTypeRepo examTypeRepository;
    private final ExamTypeMapper     mapper;

    @Override @Transactional
    public ExamTypeDto.Response create(ExamTypeDto.CreateRequest req) {
        if (examTypeRepository.existsByExamName(req.examName()))
            throw new ExamTypeException.DuplicateName(req.examName());

        ExamType examType = ExamType.builder()
                .examName(req.examName())
                .maxMark(req.maxMark())
                .build();

        return mapper.toDto(examTypeRepository.save(examType));
    }

    @Override @Transactional
    public ExamTypeDto.Response update(UUID id, ExamTypeDto.UpdateRequest req) {
        ExamType examType = findOrThrow(id);

        if (req.examName() != null) {
            if (examTypeRepository.existsByExamNameAndIdNot(req.examName(), id))
                throw new ExamTypeException.DuplicateName(req.examName());
            examType.setExamName(req.examName());
        }
        if (req.maxMark() != null) examType.setMaxMark(req.maxMark());

        return mapper.toDto(examTypeRepository.save(examType));
    }

    @Override @Transactional(readOnly = true)
    public ExamTypeDto.Response getById(UUID id) {
        return mapper.toDto(findOrThrow(id));
    }

    @Override @Transactional(readOnly = true)
    public List<ExamTypeDto.Response> getAll() {
        return mapper.toDtoList(examTypeRepository.findAll());
    }

    @Override @Transactional
    public void delete(UUID id) {
        examTypeRepository.delete(findOrThrow(id));
    }

    private ExamType findOrThrow(UUID id) {
        return examTypeRepository.findById(id)
                .orElseThrow(() -> new ExamTypeException.NotFound(id.toString()));
    }
}
