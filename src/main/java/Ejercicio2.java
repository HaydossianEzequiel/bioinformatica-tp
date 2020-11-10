import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.io.FastaReaderHelper;
import org.biojava3.core.sequence.io.util.IOUtils;
import org.biojava3.ws.alignment.qblast.*;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Ejercicio2 {

	public static void main(String[] args) {
		System.out.println("Ejecutando ejercicio 2");

		NCBIQBlastService service = new NCBIQBlastService();
		File fastaInputFile = new File("secuencia.fasta");

		Map<String, ProteinSequence> proteinSequences = new LinkedHashMap<String, ProteinSequence>();

		System.out.print("Leyendo secuencias input en formato FASTA...");

		try {
			proteinSequences = FastaReaderHelper.readFastaProteinSequence(fastaInputFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

		ProteinSequence proteina = proteinSequences.get(proteinSequences.keySet().toArray()[0]);
		NCBIQBlastAlignmentProperties props = new NCBIQBlastAlignmentProperties();
		props.setBlastProgram(BlastProgramEnum.blastp);
		props.setBlastDatabase("swissprot");
		
		NCBIQBlastOutputProperties outputProps = new NCBIQBlastOutputProperties();
		outputProps.setOutputFormat(BlastOutputFormatEnum.Text);

		String rid = null;
		FileWriter writer = null;
		BufferedReader reader = null;
		try {

			rid = service.sendAlignmentRequest(proteina.getSequenceAsString(), props);


			while (!service.isReady(rid)) {
				System.out.println("procesando");
				Thread.sleep(5000);
			}

			System.out.println("Resultados listos!");
			InputStream in = service.getAlignmentResults(rid, outputProps);
			reader = new BufferedReader(new InputStreamReader(in));

			String filename = "blast.out";

			System.out.print("Guardando los resultados en " + filename + "...");

			File f = new File(filename);
			writer = new FileWriter(f);

			String line;
			while ((line = reader.readLine()) != null) {
				writer.write(line + System.getProperty("line.separator"));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			IOUtils.close(writer);
			IOUtils.close(reader);
			service.sendDeleteRequest(rid);
		}
	}
}