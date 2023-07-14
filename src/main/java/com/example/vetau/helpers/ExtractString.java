package com.example.vetau.helpers;

public class ExtractString {
    public static int extractNumber(String chuoi) {
        StringBuilder soA = new StringBuilder();
        for (int i = chuoi.length() - 1; i >= 0; i--) {
            char kyTu = chuoi.charAt(i);
            if (Character.isDigit(kyTu)) {
                soA.insert(0, kyTu);
            } else {
                break;
            }
        }

        if (soA.length() > 0) {
            return Integer.parseInt(soA.toString());
        } else {
            return -1;
        }
    }
    public int Extract_String(String ID, String MaSo)
    {
        int STT = extractNumber(ID);
        return STT;
    }

    public static void main(String[] args) {
        System.out.println(extractNumber("SV19"));

    }
}

