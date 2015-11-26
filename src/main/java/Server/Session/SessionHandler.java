package main.java.Server.Session;

abstract public class SessionHandler
{
    protected SessionStorage sessionStorage;

    abstract public void start() throws SessionCreationException;

    abstract public void close();

    public SessionStorage getSessionStorage() {
        return sessionStorage;
    }
}