import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class DataBase {
	
    private static final String PATH = "scores.txt";
    public void saveFile(HashMap<String, Integer> users)
            throws IOException
    {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(PATH))) {
            os.writeObject(users);
        }
    }

    public HashMap<String, Integer> readFile()
            throws ClassNotFoundException, IOException
    {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(PATH))) {
            return (HashMap<String, Integer>) is.readObject();
        }
    }
    
    public void init() throws IOException {
        HashMap<String, Integer> users = new HashMap<>();
        users.put("Vanessa Bellido", 0);
        users.put("Carlos Bernal", 0);
        users.put("Jessica Valiente", 0);
        users.put("Marc Larroda McQuejas", 0);
        users.put("Ruben Hernandez", 0);
        users.put("David Carbajal", 0);
        users.put("Marco Frias", 0);
        users.put("Adria Miret", 0);
        users.put("Jorge Sanchez inici", 0);
        
        
        DataBase xd = new DataBase();
        xd.saveFile(users);
    }

	public static void main( String[] args ) throws Exception
	{
        DataBase xd = new DataBase();

        xd.init();


        

        // Read our HashMap back into memory and print it out
        HashMap<String, Integer> restored = xd.readFile();

        System.out.println(restored);
	}
	
	



	
	
}
