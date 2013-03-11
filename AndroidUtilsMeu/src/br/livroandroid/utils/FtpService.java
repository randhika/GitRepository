package br.livroandroid.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.util.Log;

public class FtpService {
    public static void enviaArquivos(File arquivo){
   	FTPClient client = new FTPClient();
   	try{
   		client.connect("ftp.guia102nocelular.com.br");
   		if(FTPReply.isPositiveCompletion(client.getReplyCode())){
   			client.login("guia102nocelular", "guia102");
   			System.out.println("conectado");
   		}else {
   			client.disconnect();
   			System.out.println("conexao recusada");
   			System.exit(1);
   		}
   		client.enterLocalPassiveMode();
   		client.changeWorkingDirectory("../imagensAndroid");
   		String[] arquivos = client.listNames();
   		
   		String arquivoLocal = arquivo.getName();
   		FileInputStream is = new FileInputStream(arquivo);
   		client.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
   		client.setFileType(FTPClient.STREAM_TRANSFER_MODE);
   		if(client.storeFile(arquivoLocal, is)){
   		    Log.i("Arquivo transferido ", arquivo.getPath());
   		    client.logout();
   		    client.disconnect();
   		}
   		if(arquivos != null){
           		for(String s : arquivos){
           		    System.out.println("Arquivos do FTP >");
           		    System.out.println(s);
           		}
   		}
   		
   	}catch (Exception e) {
   	    e.printStackTrace();
   	    Log.e("ERRO", "Erro ao enviar arquivo "+ arquivo.getPath() , e);
   	}
       }
    public static void baixaArquivos(String localFtp,String caminhoDestino, int transferMode, int fileType){
   	FTPClient client = new FTPClient();
   	String arquivoLocal="";
   	try{
   		client.connect("ftp.guia102nocelular.com.br");
   		if(FTPReply.isPositiveCompletion(client.getReplyCode())){
   			client.login("guia102nocelular", "guia102");
   			System.out.println("conectado");
   		}else {
   			client.disconnect();
   			System.out.println("conexao recusada");
   			System.exit(1);
   		}
   		client.enterLocalPassiveMode();
   		client.changeWorkingDirectory(localFtp);
   		client.setFileTransferMode(transferMode);
   		client.setFileType(fileType);
   		String[] arquivos = client.listNames();
   		FileOutputStream out;
   		for(String arquivo : arquivos){
   		    client.connect("ftp.guia102nocelular.com.br");
   		    if(FTPReply.isPositiveCompletion(client.getReplyCode())){
   			client.login("guia102nocelular", "guia102");
   			client.enterLocalPassiveMode();
   			client.changeWorkingDirectory(localFtp);
   			client.setFileTransferMode(transferMode);
   			client.setFileType(fileType);
   		    }
   		    File f = new File(arquivo);
           		if(!f.isDirectory()){
           		    arquivoLocal = caminhoDestino + "/"+arquivo;
           		    out = new FileOutputStream(arquivoLocal);
           		    if(client.retrieveFile(arquivo, out)){
           			Log.i("Arquivo transferido ", arquivo);
           			client.logout();
           			client.disconnect();
           		    }
           		    out.flush();
           		    out.close();
   		    }
   		}
   		client.disconnect();
   	}catch (IOException e) {
   	    Log.e(Contants.LOG_TAG, "Erro ao baixar arquivo " + arquivoLocal , e);
   	    e.printStackTrace();
	}
   }
}
