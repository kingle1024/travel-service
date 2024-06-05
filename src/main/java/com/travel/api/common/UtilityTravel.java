package com.travel.api.common;

public class UtilityTravel {
    public static String maskAuthor(String item) {
        if (item == null || item.length() <= 1) {
            return item; // null이거나 길이가 1 이하인 경우 그대로 반환
        }

        // 첫 글자를 제외한 나머지 부분을 '*'로 변환
        StringBuilder maskedAuthor = new StringBuilder();
        maskedAuthor.append(item.charAt(0));

        for (int i = 1; i < item.length(); i++) {
            maskedAuthor.append('*');
        }

        return maskedAuthor.toString();
    }

}
