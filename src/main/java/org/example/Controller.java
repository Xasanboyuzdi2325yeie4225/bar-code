package org.example;

import org.example.barcode.BarCode;
import org.example.btns.InterfaceBtns;
import org.example.btns.InterfaceBtnsIMPL;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Controller extends TelegramLongPollingBot {
    private final String TOKEN = "bar_code_token";
    private final String USERNAME = "bar_code_username";
    private final InterfaceBtns btns = new InterfaceBtnsIMPL();
    private final Resource resource = new Resource();
    private final BarCode barCode = new BarCode();
    private static String status = "";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (text.equals("/start") || text.equals("Bosh menu")) {
                    status = "";
                    System.out.println(text);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText(text);
                    sendMessage.setReplyMarkup(btns.btnKeyboardBTN(resource.menu()));
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                } else if (text.equals("BarCode yaratish")) {
                    System.out.println(text);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText("ixtiyoriy tekst kiriting");
                    sendMessage.setReplyMarkup(btns.btnKeyboardBTN(resource.BoshMenu()));
                    System.out.println("sendmessage sozlandi");
                    status = "create";
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                } else if (text.equals("BarCode ni o`qish")) {
                    System.out.println(text);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText(barCode.readBarCode());
                    sendMessage.setReplyMarkup(btns.btnKeyboardBTN(resource.menu()));
                    System.out.println("rasm tashlang");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                } else if (status.equals("create")) {
                    System.out.println(text);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText(barCode.createBarCode(text));
                    sendMessage.setReplyMarkup(btns.btnKeyboardBTN(resource.BoshMenu()));
                    System.out.println("sendmessage sozlandi");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(message.getChatId());
                    sendPhoto.setPhoto(new InputFile(new File("./MyBarcode.png")));
                    System.out.println("sendphoto ham sozlandi");
                    try {
                        execute(sendPhoto);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            if (message.hasPhoto()) {
                System.out.println("message.hasPhoto");
                File file1 = new File("./MyBarcode.png");
                if (file1.exists()) {
                    System.out.println("file borakan");
                    file1.delete();
                    System.out.println("file o`chirildi");
                }
                List<org.telegram.telegrambots.meta.api.objects.PhotoSize> photos = update.getMessage().getPhoto();
                String fileId = photos.get(photos.size() - 1).getFileId();
                System.out.println("update dan kelgan rasm file id si olindi");
                GetFile getFile = new GetFile();
                getFile.setFileId(fileId);

                try {
                    org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
                    String filePath = file.getFilePath();
                    downloadFile(filePath, "./MyBarcode" + ".png");
                    SendMessage sendMessage=new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText(barCode.readBarCode());
                    sendMessage.setReplyMarkup(btns.btnKeyboardBTN(resource.BoshMenu()));
                    System.out.println("ketti");
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    private void downloadFile(String filePath, String fileName) {
        try (BufferedInputStream in = new BufferedInputStream(new URL("https://api.telegram.org/file/bot" + getBotToken() + "/" + filePath).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}