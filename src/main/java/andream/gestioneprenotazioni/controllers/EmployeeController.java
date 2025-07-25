package andream.gestioneprenotazioni.controllers;

import andream.gestioneprenotazioni.entities.Employee;
import andream.gestioneprenotazioni.exceptions.ValidationException;
import andream.gestioneprenotazioni.payloads.NewEmployeeDTO;
import andream.gestioneprenotazioni.payloads.NewEmployeeResponceDTO;
import andream.gestioneprenotazioni.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeesService employeesService;

    @GetMapping
    public Page<Employee> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                  @RequestParam(defaultValue = "10") int pageSize,
                                  @RequestParam(defaultValue = "username") String sortBy
    ) {
        return this.employeesService.getAll(pageNumber, pageSize, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewEmployeeResponceDTO save(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors()
                    .stream()
                    .map(fe -> fe.getDefaultMessage())
                    .toList()
            );
        } else {
            Employee newEmployee = this.employeesService.save(payload);
            return new NewEmployeeResponceDTO(newEmployee.getId());
        }
    }

    @GetMapping("/{employeeId}")
    public Employee getById(@PathVariable UUID employeeId) {
        return this.employeesService.getByID(employeeId);
    }

    @PutMapping("/{employeeId}")
    public Employee updateUser(@PathVariable UUID employeeId, @RequestBody NewEmployeeDTO payload) {
        return this.employeesService.findAndUpdate(payload, employeeId);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID employeeId) {
        this.employeesService.findAndDelete(employeeId);
    }


    @PatchMapping("/{employeeId}/avatar")
    public String uploadImage(@RequestParam("avatar") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        return this.employeesService.uploadAvatar(file);
    }


}
