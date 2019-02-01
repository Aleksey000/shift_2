package ru.cft.starterkit.repository.implement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.cft.starterkit.entity.User;
import ru.cft.starterkit.exception.ObjectNotFoundException;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository{

    private static final Logger log = LoggerFactory.getLogger(SampleEntityRepositoryImpl.class);

    private AtomicLong idCounter = new AtomicLong();

    private Map<Long, User> storage = new ConcurrentHashMap<>();

    @Autowired
    public UserRepository(){
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        try {
            fileIn = new FileInputStream(".\\tmpdata\\users.ser");
            in = new ObjectInputStream(fileIn);
            this.storage = (Map<Long, User>) in.readObject();
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fileIn = new FileInputStream(".\\tmpdata\\usersC.ser");
            in = new ObjectInputStream(fileIn);
            this.idCounter = (AtomicLong) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveDataToFile(){
        File folder = new File(".\\tmpdata");
        if (!folder.exists()) {
            folder.mkdir();
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(".\\tmpdata\\users.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this.storage);
            out.close();
            fileOut.close();

            out = null;
            fileOut = null;

            fileOut = new FileOutputStream(".\\tmpdata\\usersC.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(this.idCounter);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User add(User user) {
        user.setId(idCounter.incrementAndGet());
        storage.put(user.getId(), user);

        log.info("Added new user to storage: {}", user);
        this.saveDataToFile();
        return user;
    }

    public User update(User user) throws ObjectNotFoundException {
        if (user == null) {
            throw new ObjectNotFoundException(String.format("User not found"));
        }
        storage.put(user.getId(), user);

        log.info("Update user to storage: {}", user);
        this.saveDataToFile();
        return user;
    }

    public User get(Long id) throws ObjectNotFoundException {
        User user = storage.get(id);

        if (user == null) {
            log.error("Failed to get user with id '{}' from storage", id);
            throw new ObjectNotFoundException(String.format("User with id %s not found", id));
        }

        log.info("Returned user with id '{}' from storage: {}", id, user);
        return user;
    }

    public User getByLogin(String login) throws ObjectNotFoundException {
        User user = null;
        for(Map.Entry<Long, User> entry: storage.entrySet()) {
            if(entry.getValue().getLogin().equals(login)) {
                return entry.getValue();
            }
        }

        if (user == null) {
            log.error("Failed to get user with login '{}' from storage", login);
            throw new ObjectNotFoundException(String.format("User with login %s not found", login));
        }

        return user;
    }
}
