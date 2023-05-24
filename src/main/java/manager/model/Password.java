package manager.model;

/**
 * Класс Password реализует объект-пароль.
 */
public class Password {

    private int id; // Идентификатор пароля
    private String name; // Название сайта (или любой тег какой юзеру взбредет в голову)
    private String login; // Логин
    private String password; // Пароль

    /**
     * Конструктор класса Password.
     *
     * @param id       Идентификатор пароля
     * @param name     Название сайта
     * @param login    Логин
     * @param password Пароль
     */
    public Password(int id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    /**
     * Возвращает идентификатор пароля.
     *
     * @return Идентификатор пароля.
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор пароля.
     *
     * @param id Идентификатор пароля.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает название пароля.
     *
     * @return Название пароля.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название сайта.
     *
     * @param name Название сайта.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает логин.
     *
     * @return Логин.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Устанавливает логин.
     *
     * @param login Логин.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Возвращает пароль.
     *
     * @return Пароль.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль.
     *
     * @param password Пароль.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Возвращает строковое представление объекта Password.
     *
     * @return Строковое представление объекта Password.
     */
    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Login: %s | Password: %s", this.id, this.name, this.login, this.password);
    }
}
