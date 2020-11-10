import org.biojava3.core.sequence.DNASequence;
import org.biojava3.core.sequence.io.GenbankReaderHelper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

public class Ejercicio1 {

    public static void main(String[] args) throws Exception{

        File dnaFile = new File("ej1_chikunguya.gb");

        LinkedHashMap<String, DNASequence> dnaSequences = GenbankReaderHelper.readGenbankDNASequence( dnaFile );
        for (DNASequence sequence : dnaSequences.values()) {
            //agregar el >1
            System.out.println( sequence.getSequenceAsString() );
            try {
                FileWriter myWriter = new FileWriter("secuencia_chikunguya.fasta");
                myWriter.write(sequence.getSequenceAsString());
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }
}