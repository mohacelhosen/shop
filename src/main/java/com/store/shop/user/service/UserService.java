package com.store.shop.user.service;

import com.store.shop.exception.DuplicateKeyException;
import com.store.shop.user.model.User;
import com.store.shop.user.repository.UserRepository;
import jakarta.el.PropertyNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public User saveUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            throw new DuplicateKeyException("User with " + user.getId() + " already exist");
        }
        return userRepository.save(user);
    }

    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new PropertyNotFoundException("User not found with " + userId));
    }

    public List<User> allUser() {
        return userRepository.findAll();
    }

    public List<User> saveListOfUser(List<User> userList) {
        return userRepository.saveAll(userList);
    }

    public User updateUserInfo(Integer userId, User user) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new DuplicateKeyException("User with " + userId + " already exist");
        }
        User dbUser = optionalUser.get();
        BeanUtils.copyProperties(user, dbUser, getNullPropertyName(user));
        dbUser.setId(userId);
        return userRepository.save(dbUser);
    }

    private String[] getNullPropertyName(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] descriptors = beanWrapper.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor descriptor : descriptors) {
            if (beanWrapper.getPropertyValue(descriptor.getName()) == null) {
                emptyNames.add(descriptor.getName());
            }
        }
        return emptyNames.toArray(emptyNames.toArray(new String[0]));
    }


    public List<User> filterAndSorting(String gender, Integer age, String blood, String country, String state, String city, String zipCode, String sort) {
        Query query = new Query();

        // Example: Add criteria for filtering by gender
        if (gender != null) {
            query.addCriteria(Criteria.where("gender").is(gender));
        }

        if (age !=null && age>0){
            query.addCriteria(Criteria.where("age").gte(age));
        }

        if (blood !=null){
            query.addCriteria(Criteria.where("bloodGroup").is(blood));
        }
        if (country !=null){
            query.addCriteria(Criteria.where("address.country").is(country));
        }

        if (state !=null){
            query.addCriteria(Criteria.where("address.state").is(state));
        }

        if (city !=null){
            query.addCriteria(Criteria.where("address.city").is(city));
        }

        if (sort !=null){
            if (sort.equalsIgnoreCase("asc")){
                query.with(Sort.by(Sort.Order.asc("age")));
            } else if (sort.equalsIgnoreCase("dsc")) {
                query.with(Sort.by(Sort.Order.desc("age")));
            }
        }else {
            query.with(Sort.by(Sort.Order.asc("age")));
        }



        List<User> result = mongoTemplate.find(query, User.class);
        return result;
    }
}
