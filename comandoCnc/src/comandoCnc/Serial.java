package comandoCnc;

import java.nio.charset.StandardCharsets;

import com.fazecast.jSerialComm.SerialPort;

public class Serial {
	// buffer para recepção e envio de dados
	byte[] readBuffer = new byte[2048];
	byte[] outputMessage = "".getBytes(StandardCharsets.UTF_8);
	
	// portas COM disponíveis
	SerialPort[] ports = SerialPort.getCommPorts();
	SerialPort serialPort;	

	// retorna um Array de String com as portas disponíveis
	public String[] listaCom() {
		String[] comPort = new String[ports.length];
		if (ports.length == 0) {
			String[] aux = new String[1];
			aux[0] = "Nenhuma COM";
			return aux;
		} else {
			for (int i = 0; i < ports.length; ++i) {
				comPort[i] = ports[i].getSystemPortName();
			}
		}
		return comPort;
	}

	// abre a porta COM
	public boolean abreCom(String port, int baudRate) {
		int posicao = -1;
		try {
			// busca a COM na lista de portas
			for (int i = 0; i < ports.length; ++i) {
				if (ports[i].getSystemPortName().equals(port)) {
					posicao = i;
					break;
				}
			}
			serialPort = ports[posicao];
			serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
			serialPort.setBaudRate(baudRate);
			return serialPort.openPort();
		} catch (Exception erro) {
			return false;
		}
	}

	// fecha a porta COM
	public void fechaCom() {
		if(serialPort != null)
			serialPort.closePort();
		else System.out.println("porta serial ja esta fechada");
	}

	// envia dados
	public void enviaDados(String dados) {
		outputMessage = dados.getBytes(StandardCharsets.UTF_8);
		serialPort.writeBytes(outputMessage, outputMessage.length);
	}
	
	// le dados
	public String leDados() {
		int numRead = serialPort.readBytes(readBuffer, readBuffer.length);
		if (numRead > 0) {
			String receivedData = new String(readBuffer, 0, numRead);
			return receivedData;
		}
		return null;
	}	
}
