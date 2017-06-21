package edu.eezo.tafl;

/**
 * Created by Eezo on 21.06.2017.
 */
public final class Data {
    // Database string constants
    static String DB_CONNECTION_SUCCESSFUL = "Connection successful.";
    static String DB_CONNECTION_CLOSED = "Connection closed.";
    static String DB_RESULT_SET_CLOSED = "ResultSet closed.";
    static String DB_STATEMENT_CLOSED = "Statement closed.";
    static String DB_CONNECTION_FAILURE = "Connection failure.";
    static String DB_CONNECTION_ERROR = "Не вдалося під'єднатися до бази данних.";
    static String DB_NO_CONNECTION = "Ви не під'єднані до бази даних. Виконання запитів неможливе.";
    static String DB_MISSING_JDBC_CLASS = "Клас для JDBC відсутній.";
    static String DB_WRONG_PATH = "Можливо, не вірно вказана адреса бази даних.\nБажаєте вказати місцезнахождення файлу?\nПримітка: программа не приймає назви з кирилицею.";
    static String WITHOUT_DB_RESTRICTIONS = "Без під'єднаної БД программа здатна тільки виводити таблицю токенів.";
    static String DB_HELP_TITLE = "Спрощення та обмеження";
    static String DB_HELP = "Правила по керуванню БД:\n"
            + " - ім’я таблиці складається із сукупності літер і цифр, що починається з літери. Довжина не більше 8 символів\n"
            + " - ім’я атрибута складається із сукупності літер, цифр, символів «_» і «-», що починається з літери або символа «_». Довжина не більше 16 символів";

    // Syntax Analization Messages
    static String SYNTAX_ANALIZATOR = "Синтаксичний аналізатор";
    static String SYNTAX_FILE_NOT_FOUND = "Файлу з заданим синтаксисом не знайдено.\nВиберіть потрібний файл.";
    static String SYNTAX_NO_SYNTAX = "Синтаксичний аналізатор не працює без заданого синтаксису.";

    // Semantic Analizator Messages
    static String SEMANTIC_ANALIZATOR = "Синтаксичний аналізатор";
    static String SEMANTIC_AFTER_COMPARATIONS_SYMBOLS = "Після знаків порівняння повинно йти тільки число або поле.";
    static String SEMANTIC_AFTER_MATH_OPS = "Після знака математичної операції повинно йти тільки число або поле.";

    // Lexical Analizator Messages
    static String LEXICAL_ANALIZATOR = "Лексичний аналізатор";
    static String LEXICAL_UNKOWN_LEXEMS = "Увага! Запит містить невизначені лексеми.";

    static String WARNING = "warning";
    static String ERROR = "error";
}
