package ru.endlesscode.badger;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.endlesscode.badger.misc.Config;

/**
 * Created by OsipXD on 14.03.2016
 * It is part of the Badger.
 * All rights reserved 2014 - 2016 © «EndlessCode Group»
 */
public class Entry {
    private final int id;

    @Nullable
    private final String surname;
    private final String name;
    @Nullable
    private final String patronymic;
    @Nullable
    private final String addInfo;
    @Nullable
    private final String quote;

    private final EntryType type;

    public Entry(int id, @NotNull String surname, @Nullable String name, @Nullable String patronymic,
                 @NotNull String type, @Nullable String addInfo, @Nullable String quote) {
        this.id = id;

        if (name == null) {
            this.surname = null;
            this.name = surname;
        } else {
            this.surname = surname;
            this.name = name;
        }

        this.patronymic = patronymic;
        this.type = EntryType.convert(type);
        this.addInfo = addInfo;
        this.quote = quote;
    }

    public String getFileName() {
        String fileName = "";
        boolean empty = true;

        if (this.hasSurname()) {
            fileName += this.surname;
            empty = false;
        }

        if (this.hasName()) {
            if (!empty) {
                fileName += "_";
            }

            fileName += this.name;
        }

        if (this.hasPatronymic()) {
            fileName += "_" + this.patronymic;
        }
        
        return (this.id < 10 ? "0" : "") + this.id + "_" + fileName;
    }

    public String getVisibleName() {
        String visibleName = name;

        if (this.hasPatronymic()) {
            visibleName += " " + patronymic;
        }

        if (Config.NAME_UPPER_CASE) {
            visibleName = visibleName.toUpperCase();
        }

        return visibleName;
    }

    @Contract(pure = true)
    private boolean hasPatronymic() {
        return this.patronymic != null;
    }

    @Contract(pure = true)
    private boolean hasName() {
        return this.name != null;
    }

    @Contract(pure = true)
    public boolean hasSurname() {
        return this.surname != null;
    }

    public String getVisibleSurname() {
        return !Config.NAME_UPPER_CASE || surname == null ? surname : surname.toUpperCase();
    }

    public String getVisibleAddInfo() {
        return !Config.INFO_UPPER_CASE || addInfo == null ? addInfo : addInfo.toUpperCase();
    }

    @Nullable
    public String getQuote() {
        return quote;
    }

    public EntryType getType() {
        return type;
    }

    public boolean hasAddInfo() {
        return this.addInfo != null;
    }

    public boolean hasQuote() {
        return quote != null;
    }

    public enum EntryType {
        PUPIL("ш"), WORKER("с"), GUEST("г"), NOBODY("н");

        private final String start;

        EntryType(String start) {
            this.start = start;
        }

        public static EntryType convert(String typeString) {
            for (Entry.EntryType type : Entry.EntryType.values()) {
                if (typeString.startsWith(type.start)) {
                    return type;
                }
            }

            return PUPIL;
        }
    }
}
