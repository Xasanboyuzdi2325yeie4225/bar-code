package org.example.btns;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class InterfaceBtnsIMPL implements InterfaceBtns{
    @Override
    public ReplyKeyboardMarkup btnKeyboardBTN(List<String> list) {
        ReplyKeyboardMarkup replyKeyboardMarkup=new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow>l=new ArrayList<>();
        int q=0;
        for (int i = 0; i < list.size()/2; i++) {
            KeyboardRow row=new KeyboardRow();
            for (int i1 = 0; i1 < 2; i1++) {
                KeyboardButton btn1=new KeyboardButton();
                btn1.setText(list.get(q));
                q++;
                row.add(btn1);
            }
            l.add(row);
        }
        KeyboardRow row1=new KeyboardRow();

        if (list.size()%2==1){
            KeyboardButton btn=new KeyboardButton();
            btn.setText(list.get(list.size()-1));
            row1.add(btn);
        }
        l.add(row1);
        replyKeyboardMarkup.setKeyboard(l);
        return replyKeyboardMarkup;
    }

    @Override
    public InlineKeyboardMarkup btnInlineKeyboardBTN(List<String> key, List<String> value) {
        InlineKeyboardMarkup inlineKeyboardMarkup=new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>>listList=new ArrayList<>();
        int q=0;
        for (int i = 0; i < value.size()/2; i++) {
            List<InlineKeyboardButton>list=new ArrayList<>();
            for (int i1 = 0; i1 < 2; i1++) {
                InlineKeyboardButton btn1=new InlineKeyboardButton();
                btn1.setText(value.get(q));
                btn1.setCallbackData(key.get(q));
                q++;
                list.add(btn1);
            }
            listList.add(list);
        }
        List<InlineKeyboardButton>list=new ArrayList<>();
        if (value.size()%2==1){
            InlineKeyboardButton btn1=new InlineKeyboardButton();
            btn1.setText(value.get(value.size()-1));
            btn1.setCallbackData(key.get(value.size()-1));
            list.add(btn1);
        }
        listList.add(list);
        inlineKeyboardMarkup.setKeyboard(listList);
        return inlineKeyboardMarkup;
    }


}
