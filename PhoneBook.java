import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneBook {
    private static HashMap<String, String> phBook = new HashMap<String, String>();

    //addPB - добавляет запись по заданным номеру телефона и фамилии
    private static void addPB(String phone, String name) {
        phBook.put(phone, name);
    }

    //delPB - удаляет запись по номеру телефона
    private static void delPB(String phone) {
        phBook.remove(phone);
    }

    //savePB - сохраняет БД в текстовом файле phone.txt
    private static void savePB() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("phone.txt")));
        for(Map.Entry<String,String> k: phBook.entrySet()){
            writer.write(k.getKey() + " " + k.getValue()+System.lineSeparator());
        }
        writer.close();
    }

    //loadPB - загружает контакты
    public static void loadPB() throws IOException {
        File file = new File("phone.txt");
        if (file.exists()){
            BufferedReader reader = new BufferedReader(new FileReader(new File("phone.txt")));
            String action;
            while ((action=reader.readLine())!=null) {
                String[] dat = action.split(" ");
                phBook.put(dat[0], dat[1]);
            }
            reader.close();
        }
    }

    //PrintPhonebook - выводит контакты
    public static void PrintPhonebook(){
        System.out.println("Телефонный справочник: ");
        for(Map.Entry<String,String> k: phBook.entrySet()){
            System.out.println(k.getValue()+": "+ k.getKey());
        }
    }

    //FindSurname - производит поиск фамилии по номеру телефона 
    public static String FindSurname(String number){
        String result = phBook.get(number);
        if (result == null) return "абонент с таким номером не найдей";
        return result;
    }

    //FindNumberPhone - производит поиск списка номеров по фамилии 
    public static String[] FindNumberPhone(String surname){
        ArrayList <String> result = new ArrayList<String>();
        for (Map.Entry entry : phBook.entrySet()) {
            if (surname.equalsIgnoreCase((String)entry.getValue())){
                result.add((String)entry.getKey());
            }
        }
        if (result.size() == 0) result.add("абонент с такой фамилией не найден");
        return result.toArray(new String[0]);
    }

    public static void main(String[] args) throws IOException {
      
        String action;
        loadPB();
        PrintPhonebook();
        System.out.println("выбор действия: 1 - добавить данные, 2 - удалить данные, 3 -  найти номера по фамилии, 4 - найти фамилию, " +
                            " 5 - сохранить, 6 - список,  11 - выход");

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        action = bf.readLine();
        while(!action.equals("11")){


            if(action.equals("6")){
            phBook.entrySet().stream()
                .sorted(Map.Entry.<String, String>comparingByValue())
                .forEach(System.out::println);
            }



            //добавление записи
            if(action.equals("1")){
                System.out.println("Введите фамилию:");
                String name = bf.readLine();
                System.out.println("Введите телефон:");
                String phone = bf.readLine();
                addPB(phone, name);
            }else{
                //удаление записи
                if(action.equals("2")){
                    System.out.println("Введите телефон:");
                    String phone = bf.readLine();
                    delPB(phone);
                }else{
                    //поиск номеров по фамилии
                    if (action.equals("3")){
                        System.out.println("Введите фамилию:");
                        String surname = bf.readLine();
                        String[] numbers = FindNumberPhone(surname);
                        for (String number : numbers) {
                            System.out.println(number);
                        }
                    } else {
                        //поиск фамилии по номеру
                        if (action.equals("4")) {
                            System.out.println("Введите номер:");
                            String number = bf.readLine();
                            System.out.println(FindSurname(number));
                        } else {
                            //сохранение БД в файл
                            if(action.equals("5")){
                                savePB();
                            }
                        }
                    }  
                }
            }
            //запрос на следующее действие
            
            System.out.println("выбор действия: 1 - добавить данные, 2 - удалить данные, 3 -  найти номера по фамилии, 4 - найти фамилию, " +
                            " 5 - сохранить, 6 - список,  11 - выход");
            action = bf.readLine();
        }
    }
}