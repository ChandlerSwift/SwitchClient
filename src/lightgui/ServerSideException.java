/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightgui;

/**
 *
 * @author Chandler Swift
 */
class ServerSideException extends Exception {
    public ServerSideException() { super(); }
    public ServerSideException(int code) { super(Integer.toString(code) + " Error"); }
    public ServerSideException(String message) { super(message); }
}
