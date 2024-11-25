import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class SimetricaAES {
    private static SecretKey carregarChave(String chaveBase64) {
        byte[] chaveBytes = Base64.getDecoder().decode(chaveBase64);
        return new SecretKeySpec(chaveBytes, "AES");
    }

    private static String criptografar(String mensagem, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] mensagemCriptografada = cipher.doFinal(mensagem.getBytes());
        return Base64.getEncoder().encodeToString(mensagemCriptografada);
    }

    private static String descriptografar(String mensagemCriptografada, SecretKey chave) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, chave);
        byte[] mensagemDecodificada = Base64.getDecoder().decode(mensagemCriptografada);
        byte[] mensagemOriginal = cipher.doFinal(mensagemDecodificada);
        return new String(mensagemOriginal);
    }
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            boolean continuar = true;

            while (continuar) {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1. Criptografar uma mensagem");
                System.out.println("2. Descriptografar uma mensagem");
                System.out.println("3. Sair");
                System.out.print("Opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.print("Digite a mensagem para criptografar: ");
                        String mensagemOriginal = scanner.nextLine();

                        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                        keyGen.init(128);
                        SecretKey chaveSecreta = keyGen.generateKey();
                        String chaveBase64 = Base64.getEncoder().encodeToString(chaveSecreta.getEncoded());

                        String mensagemCriptografada = criptografar(mensagemOriginal, chaveSecreta);

                        System.out.println("Mensagem criptografada: " + mensagemCriptografada);
                        System.out.println("Chave secreta (Base64): " + chaveBase64);
                        break;

                    case 2:
                        System.out.print("Digite a chave secreta (Base64): ");
                        String chaveInserida = scanner.nextLine();

                        System.out.print("Digite a mensagem criptografada: ");
                        String mensagemCriptografadaEntrada = scanner.nextLine();

                        SecretKey chaveDecodificada = carregarChave(chaveInserida);

                        String mensagemDescriptografada = descriptografar(mensagemCriptografadaEntrada, chaveDecodificada);

                        System.out.println("Mensagem descriptografada: " + mensagemDescriptografada);
                        break;

                    case 3:
                        System.out.println("Encerrando o programa. Até logo!");
                        continuar = false;
                        break;

                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}