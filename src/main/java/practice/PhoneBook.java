package practice;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class PhoneBook {

    private Map<String, Set<String>> phoneBook = new TreeMap<>();
    // Регулярное выражение для проверки телефона
    private final Pattern phonePattern = Pattern.compile("^\\d{11}$");
    //Регулярное выражение для проверки имени
    private final Pattern namePattern = Pattern.compile("^[A-Za-zА-Яа-я]+$");

    public void addContact(String phone, String name) {
        // проверьте корректность формата имени и телефона
        // (рекомедуется написать отдельные методы для проверки является строка именем/телефоном)
        // если такой номер уже есть в списке, то перезаписать имя абонента
        if (!isValidPhone(phone)) {
            System.out.println("Неверный формат телефона");
            return;
        }
        if (!isValidName(name)) {
            System.out.println("Неверный формат имени");
            return;
        }

        // Проверяем, существует ли уже этот номер в любом контакте
        for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
            if (entry.getValue().contains(phone)) {
                // Если номер уже существует, удаляем его из старого контакта
                entry.getValue().remove(phone);
                if (entry.getValue().isEmpty()) {
                    phoneBook.remove(entry.getKey()); // Удаляем контакт, если у него больше нет номеров
                }
                break;
            }
        }
        // Добавляем номер в новый или существующий контакт
        phoneBook.computeIfAbsent(name, k -> new TreeSet<>()).add(phone);

        System.out.println("Контакт сохранен!");
    }

    public String getContactByPhone(String phone) {
        // формат одного контакта "Имя - Телефон"
        // если контакт не найдены - вернуть пустую строку
        for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
            if (entry.getValue().contains(phone)) {
                return entry.getKey() + " - " + phone;
            }
        }
        return "";
    }

    public Set<String> getContactByName(String name) {
        // формат одного контакта "Имя - Телефон"
        // если контакт не найден - вернуть пустой TreeSet
        if (phoneBook.containsKey(name)) {
            Set<String> result = new TreeSet<>();
            for (String phone : phoneBook.get(name)) {
                result.add(name + " - " + phone);
            }
            return result;
        }
        return new TreeSet<>();
    }

    public Set<String> getAllContacts() {
        // формат одного контакта "Имя - Телефон"
        // если контактов нет в телефонной книге - вернуть пустой TreeSet
        Set<String> result = new TreeSet<>();
        for (Map.Entry<String, Set<String>> entry : phoneBook.entrySet()) {
            result.add(entry.getKey() + " - " + String.join(", ", entry.getValue()));
        }
        return result;
    }

    private boolean isValidPhone(String phone) {
        return phonePattern.matcher(phone).matches();
    }

    private boolean isValidName(String name) {
        return namePattern.matcher(name).matches();
    }

    // для обхода Map используйте получение пары ключ->значение Map.Entry<String,String>
    // это поможет вам найти все ключи (key) по значению (value)
    /*
        for (Map.Entry<String, String> entry : map.entrySet()){
            String key = entry.getKey(); // получения ключа
            String value = entry.getValue(); // получения ключа
        }
    */
}