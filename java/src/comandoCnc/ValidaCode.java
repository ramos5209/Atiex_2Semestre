package comandoCnc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidaCode {
	 public static boolean isValidGCode(String line) {
	        // Regex para validar o código inicial (G ou M) e os dígitos seguintes.
	        String codePattern = "^(G|M)(\\d{1,2})(.*)$";
	        Pattern pattern = Pattern.compile(codePattern);
	        Matcher matcher = pattern.matcher(line.trim());
	        
	        if (matcher.matches()) {
	            String codeType = matcher.group(1); // G ou M
	            int codeNumber = Integer.parseInt(matcher.group(2)); // Número do código
	            String parameters = matcher.group(3).trim(); // Parâmetros (se houver)
	            
	            // Verificar se o código básico está no intervalo conhecido.
	            if ((codeType.equals("G") && codeNumber >= 0 && codeNumber <= 99) ||
	                (codeType.equals("M") && codeNumber >= 0 && codeNumber <= 99)) {
	                
	                // Se for código G ou M e necessita de parâmetros, validar os parâmetros.
	                if (codeType.equals("G")) {
	                    return validateGCodeParameters(codeNumber, parameters);
	                } else if (codeType.equals("M")) {
	                    return validateMCodeParameters(codeNumber, parameters);
	                }
	                
	                // Código básico válido.
	                return parameters.isEmpty(); // Códigos G e M sem parâmetros adicionais são válidos.
	            }
	        }
	        
	        // Se não corresponder ao padrão ou não estiver no intervalo conhecido.
	        return false;
	    }
	    
	    private static boolean validateGCodeParameters(int codeNumber, String parameters) {
	        // Validação básica de parâmetros para código G.
	        // Exemplo simples: G1, G2 necessitam de parâmetros X, Y (opcional Z).
	        String paramPattern = "";
	        
	        switch (codeNumber) {
	            case 0: 
	                paramPattern = "^(?=.*\\b(X-?\\d+(\\.\\d+)?)\\b|\\b(Y-?\\d+(\\.\\d+)?)\\b|\\b(Z-?\\d+(\\.\\d+)?)\\b)((X-?\\d+(\\.\\d+)?)?\\s*(Y-?\\d+(\\.\\d+)?)?\\s*(Z-?\\d+(\\.\\d+)?)?\\s*(F-?\\d+(\\.\\d+)?)?)$";
	                break;
	            case 1: // G1 X10 Y20 (Movimento linear)
	                paramPattern = "^(?=.*\\b(X-?\\d+(\\.\\d+)?)\\b|\\b(Y-?\\d+(\\.\\d+)?)\\b|\\b(Z-?\\d+(\\.\\d+)?)\\b)((X-?\\d+(\\.\\d+)?)?\\s*(Y-?\\d+(\\.\\d+)?)?\\s*(Z-?\\d+(\\.\\d+)?)?\\s*(F-?\\d+(\\.\\d+)?)?)$";
	                break;
	            case 2: // G2 X10 Y20 R5 (Movimento circular)
	                paramPattern = "^(X-?\\d+(\\.\\d+)?\\s+Y-?\\d+(\\.\\d+)?\\s+R-?\\d+(\\.\\d+)?(\\s+F-?\\d+(\\.\\d+)?)?)$";
	                break;
	            case 3: // G3 X10 Y20 R5 (Movimento circular)
	                paramPattern = "^(X-?\\d+(\\.\\d+)?\\s+Y-?\\d+(\\.\\d+)?\\s+R-?\\d+(\\.\\d+)?(\\s+F-?\\d+(\\.\\d+)?)?)$";
	                break;
	            default:
	                // Para códigos que não necessitam de parâmetros, assegura que não há texto extra
	                // que não seja outro código G
	                return validateNoExtraParameters(parameters);
	        }
	        
	        Pattern pattern = Pattern.compile(paramPattern);
	        Matcher matcher = pattern.matcher(parameters);
	        
	        return matcher.matches();
	    }

	    private static boolean validateMCodeParameters(int codeNumber, String parameters) {
	        // Validação básica de parâmetros para código M.
	        String paramPattern = "";

	        switch (codeNumber) {
	            case 3: // M3 S1000 (Ligar spindle com velocidade)
	                paramPattern = "^(S-?\\d+(\\.\\d+)?\\s*(M3)?)$";
	                break;
	            case 4: // M4 S1000 (Ligar spindle no sentido anti-horário com velocidade)
	                paramPattern = "^(S-?\\d+(\\.\\d+)?\\s*(M4)?)$";
	                break;
	            case 5: // M5 (Desligar spindle)
	                paramPattern = "^()$"; // Não aceita parâmetros
	                break;
	            default:
	                // Para códigos que não necessitam de parâmetros, assegura que não há texto extra
	                // que não seja outro código M
	                return validateNoExtraParameters(parameters);
	        }

	        Pattern pattern = Pattern.compile(paramPattern);
	        Matcher matcher = pattern.matcher(parameters);

	        return matcher.matches();
	    }
	    
	    private static boolean validateNoExtraParameters(String parameters) {
	        if (parameters.isEmpty()) {
	            return true;
	        }
	        
	        // Permitir apenas outros códigos G ou M sem parâmetros adicionais
	        String extraCodePattern = "^((G\\d{1,2})|(M\\d{1,2})\\s*)*$";
	        Pattern pattern = Pattern.compile(extraCodePattern);
	        Matcher matcher = pattern.matcher(parameters.trim());
	        
	        return matcher.matches();
	    }

}
