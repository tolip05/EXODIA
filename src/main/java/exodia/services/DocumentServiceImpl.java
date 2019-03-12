package exodia.services;

import exodia.entities.Document;
import exodia.models.service.DocumentServiceModel;
import exodia.repositoris.DocumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, ModelMapper modelMapper) {
        this.documentRepository = documentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DocumentServiceModel scheduleDocument(DocumentServiceModel documentServiceModel) {
        Document document = this.modelMapper.map(documentServiceModel,Document.class);
        try{
            document = this.documentRepository.saveAndFlush(document);
            return this.modelMapper.map(document,DocumentServiceModel.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DocumentServiceModel findDocumentById(String id) {
        Document document = this.documentRepository.findById(id).orElse(null);
        if (document == null){
            return null;
        }
        return this.modelMapper.map(document,DocumentServiceModel.class);
    }

    @Override
    public List<DocumentServiceModel> findAllDocument() {
        return this.documentRepository.findAll().stream()
                .map(u -> this.modelMapper.map(u,DocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean printDocument(String id) {
        try{
            this.documentRepository.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}