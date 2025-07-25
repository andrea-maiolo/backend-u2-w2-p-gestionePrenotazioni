package andream.gestioneprenotazioni.services;

import andream.gestioneprenotazioni.entities.Employee;
import andream.gestioneprenotazioni.exceptions.BadRequestException;
import andream.gestioneprenotazioni.exceptions.NotFoundException;
import andream.gestioneprenotazioni.payloads.NewEmployeeDTO;
import andream.gestioneprenotazioni.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeesService {
    @Autowired
    private EmployeeRepo employeeRepo;

    public Employee save(NewEmployeeDTO payload) {
        this.employeeRepo.findByEmail(payload.email()).ifPresent(e -> {
            throw new BadRequestException("user already registred on this email");
        });
        String newUseAvatarURL = "https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname();
        Employee newEmployee = new Employee(payload.email(), payload.username(), payload.surname(), payload.name(), newUseAvatarURL);
        this.employeeRepo.save(newEmployee);
        return newEmployee;
    }


    public Page<Employee> getAll(int pageNumber, int pageSize, String sort) {
        if (pageSize > 10) pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort).descending());
        return this.employeeRepo.findAll(pageable);
    }


    public Employee getByID(UUID employeeId) {
        return this.employeeRepo.findById(employeeId).orElseThrow(() -> new NotFoundException("user not found"));
    }

    public Employee findAndUpdate(NewEmployeeDTO payload, UUID employeeId) {
        Employee found = this.getByID(employeeId);

        if (!found.getEmail().equals(payload.email())) {
            this.employeeRepo.findByEmail(payload.email()).ifPresent(e -> {
                throw new BadRequestException("email already registred");
            });
        }

        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setEmail(payload.email());
        found.setUsername(payload.username());
        found.setAvatarUrl("https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname());

        this.employeeRepo.save(found);
        return found;
    }

    public void findAndDelete(UUID employeeId) {
        Employee found = this.employeeRepo.findById(employeeId).orElseThrow(() -> new NotFoundException("user not found"));
        this.employeeRepo.delete(found);
    }


    //patch
//        public String uploadAvatar(MultipartFile file) {
//            try {
//                Map result = imgUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//                String imageURL = (String) result.get("url"); // Dalla risposta di Cloudinary leggo l'url.
//                // ... Qua dovreste aggiornare l'avatar dell'utente a DB!
//                return imageURL;
//            } catch (Exception e) {
//                throw new BadRequestException("Ci sono stati problemi nel salvataggio del file!");
//            }
//        }

}
