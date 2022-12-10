package com.modelo.logica.enfermedades;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.controlador.persistencia.entidades.enfermedad.EnfermedadTambo;
import com.controlador.persistencia.entidades.enfermedad.GravedadTambo;
import com.controlador.persistencia.servicios.enfermedad.EnfermedadBean;
import com.controlador.servicios.ConstructorFecha;
import com.vista.enfermedades.EnfermedadDTO;
import com.vista.enfermedades.EnfermedadHistoricaDTO;
import com.vista.enfermedades.GravedadDTO;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

@Stateless
@LocalBean
public class GestionEnfermedadService implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	EnfermedadBean enfermedadDAO;
	
	public GestionEnfermedadService() {
	}
	
	public EnfermedadDTO fromEnfermedadTambo(EnfermedadTambo enfermedadTambo) {
		return EnfermedadDTO.builder()
				.idEnfermedad(enfermedadTambo.getIdenfermedad())
				.nombre(enfermedadTambo.getNombre())
				.descripcion(enfermedadTambo.getDescripcion())
				.tratamiento(enfermedadTambo.getTratamiento())
				.variante(enfermedadTambo.getVariante())
				.gravedad(fromGravedadTambo(enfermedadTambo.getGravedad()))
				.build();	
	}
	
	public EnfermedadTambo toEnfermedadTambo(EnfermedadDTO enfermedadDTO) {
		return EnfermedadTambo.builder()
				.idenfermedad(enfermedadDTO.getIdEnfermedad())
				.nombre(enfermedadDTO.getNombre())
				.descripcion(enfermedadDTO.getDescripcion())
				.tratamiento(enfermedadDTO.getTratamiento())
				.variante(enfermedadDTO.getVariante())
				.gravedad(toGravedadTambo(enfermedadDTO.getGravedad()))
				.build();
	}
	
	public GravedadDTO fromGravedadTambo(GravedadTambo gravedadTambo) {
		return GravedadDTO.builder()
				.idGravedad(gravedadTambo.getIdGravedad())
				.gravedad(gravedadTambo.getGravedad())
				.build();
	}
	
	public GravedadTambo toGravedadTambo(GravedadDTO gravedadDTO) {
		return GravedadTambo.builder()
				.idGravedad(gravedadDTO.getIdGravedad())
				.gravedad(gravedadDTO.getGravedad())
				.build();
	}
	
	public List<EnfermedadDTO> listarEnfermedades() {
		List<EnfermedadTambo> enfermedadesTambo= enfermedadDAO.listar();
		List<EnfermedadDTO> listaEnfermedades= new ArrayList<EnfermedadDTO>();
		for(EnfermedadTambo et: enfermedadesTambo){
			listaEnfermedades.add(fromEnfermedadTambo(et));
		}
		return listaEnfermedades;
	}
	
	public boolean importarDatos() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat decimalFormatter = new DecimalFormat("#");

         File directoryPath = new File("C:\\data");
         File filesList[] =directoryPath.listFiles();
         File archivoFinal= filesList[0];


        FileInputStream fis = new FileInputStream(archivoFinal);
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        HSSFSheet sheet = wb.getSheetAt(0);
        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
        try {
            eliminarDatosHistoricosEnfermedad();
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { 
                int idTernera = (int) sheet.getRow(i).getCell(0).getNumericCellValue();
                String fecRegistro = dateFormat.format(sheet.getRow(i).getCell(4).getDateCellValue());

                EnfermedadDTO enfermedad = devolverEnfermedadPorNombre(sheet.getRow(i).getCell(1).getStringCellValue(), (int) sheet.getRow(i).getCell(3).getNumericCellValue(),sheet.getRow(i).getCell(2).getStringCellValue());
                int idEnfermedad = Integer.valueOf(enfermedad.getIdEnfermedad().toString());

                cargarDatosHistoricosEnfermedad(idEnfermedad, idTernera, fecRegistro);

            }

            archivoFinal.delete();
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }
	
	public void eliminarDatosHistoricosEnfermedad() {
        enfermedadDAO.eliminarDatosHistoricosEnfermedad();
    }
	
	public EnfermedadDTO devolverEnfermedadPorNombre(String nombreEnfermedad, int gravedad, String variante) {
        return fromEnfermedadTambo(enfermedadDAO.devolverEnfermedadPorNombre(nombreEnfermedad, gravedad, variante));
    }
	
	public EnfermedadDTO listarEnfermedadxId(int idEnfermedad) {
		return fromEnfermedadTambo(enfermedadDAO.devolverEnfermedadPorId(idEnfermedad));
	}
	
	public EnfermedadDTO listarEnfermedadxNombre(String nombreEnfermedad,int gravedad, String variante) {
		return fromEnfermedadTambo(enfermedadDAO.devolverEnfermedadPorNombre(nombreEnfermedad, gravedad, variante));
	}
	
	public boolean cargarDatosHistoricosEnfermedad(int idEnfermedad, int idTernera, String fecDeteccion) {
		return enfermedadDAO.cargarDatosHistoricosEnfermedad(idEnfermedad, idTernera, fecDeteccion);
	}
	
	public List<EnfermedadHistoricaDTO> ListarEnfermedadesHistorico(){
		try {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<Object[]> historicos=new ArrayList();
		historicos=enfermedadDAO.listarEnfermedadesHistorica();
		List<EnfermedadHistoricaDTO> listaHistoricos=new ArrayList<EnfermedadHistoricaDTO>();
		for(int i = 0; i < historicos.size(); i++) {
			EnfermedadHistoricaDTO eHis=new EnfermedadHistoricaDTO();
			eHis.setSNIG(Integer.valueOf(historicos.get(i)[0].toString()));
			eHis.setNombreEnfermedad(historicos.get(i)[1].toString());
			eHis.setVariante(historicos.get(i)[2].toString());
			eHis.setSeveridad(historicos.get(i)[3].toString());
			eHis.setFecDeteccion(ConstructorFecha.stringToDate(formatter.format((Date)historicos.get(i)[4])));
			listaHistoricos.add(eHis);
		}
		return listaHistoricos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<EnfermedadHistoricaDTO> ListarEnfermedadesHistoricoxNombre(String nombre){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			List<Object[]> historicos=new ArrayList();
			historicos=enfermedadDAO.filtrarEnfermedadesHistoricasNombre(nombre);
			List<EnfermedadHistoricaDTO> listaHistoricos=new ArrayList<EnfermedadHistoricaDTO>();
			for(int i = 0; i < historicos.size(); i++) {
				EnfermedadHistoricaDTO eHis=new EnfermedadHistoricaDTO();
				eHis.setSNIG(Integer.valueOf(historicos.get(i)[0].toString()));
				eHis.setNombreEnfermedad(historicos.get(i)[1].toString());
				eHis.setVariante(historicos.get(i)[2].toString());
				eHis.setSeveridad(historicos.get(i)[3].toString());
				eHis.setFecDeteccion(ConstructorFecha.stringToDate(formatter.format((Date)historicos.get(i)[4])));
				listaHistoricos.add(eHis);
			}
			return listaHistoricos;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

	}
	
	public List<EnfermedadHistoricaDTO> ListarEnfermedadesHistoricoxSnig(int snig){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			List<Object[]> historicos=new ArrayList();
			historicos=enfermedadDAO.filtrarEnfermedadesHistoricasSnig(snig);
			List<EnfermedadHistoricaDTO> listaHistoricos=new ArrayList<EnfermedadHistoricaDTO>();
			for(int i = 0; i < historicos.size(); i++) {
				EnfermedadHistoricaDTO eHis=new EnfermedadHistoricaDTO();
				eHis.setSNIG(Integer.valueOf(historicos.get(i)[0].toString()));
				eHis.setNombreEnfermedad(historicos.get(i)[1].toString());
				eHis.setVariante(historicos.get(i)[2].toString());
				eHis.setSeveridad(historicos.get(i)[3].toString());
				eHis.setFecDeteccion(ConstructorFecha.stringToDate(formatter.format((Date)historicos.get(i)[4])));
				listaHistoricos.add(eHis);
			}
			return listaHistoricos;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
	
}
