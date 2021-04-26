package com.dicka.springfirebase.controller;

import com.dicka.springfirebase.model.Employee;
import com.dicka.springfirebase.service.FirebaseInitializer;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class EmployeeController {

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    @GetMapping(value = "/all")
    public List<Employee> all() throws ExecutionException, InterruptedException {
        List<Employee> employees = new ArrayList<>();
        CollectionReference employee = firebaseInitializer.getFirebase().collection("Employee");
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = employee.get();
        for (DocumentSnapshot documentSnapshot : querySnapshotApiFuture.get().getDocuments()){
            employees.add(documentSnapshot.toObject(Employee.class));
        }
        return employees;
    }

    @PostMapping(value = "/save")
    public Employee save(@RequestBody Employee employee){
        CollectionReference collectionReference = firebaseInitializer.getFirebase()
                .collection("Employee");
        collectionReference.document(String.valueOf(employee.getId())).set(employee);
        return employee;
    }
}
