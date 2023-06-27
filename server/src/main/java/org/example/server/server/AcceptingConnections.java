package org.example.server.server;

//import org.example.Request;
//import org.example.TypeOfAnswer;
//import org.example.models.*;
//import org.example.server.server.DataBase.MainDB;
//import org.example.server.server.commands.ExitSaver;
//import org.example.xmlUtils.XmlFileHandler;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.DatagramChannel;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.ZoneId;
//import java.util.Stack;
//import java.util.concurrent.ExecutionException;
//import java.util.logging.Logger;
//
//public class AcceptingConnections {
//    private final Deliver deliver;
//    private XmlFileHandler xmlFileHandler;
//    private Receiver receiver;
//    private final MainDB mainDB;
//
//    private final DatagramChannel datagramChannel;
//    private byte[] buffer;
//    private InetSocketAddress address;
//    final Logger logger = Logger.getLogger(Receiver.class.getCanonicalName());
//
//
//    public AcceptingConnections(Deliver deliver, Receiver areceiver , MainDB amainDB, DatagramChannel datagramChannel) {
//        this.deliver = deliver;
//        receiver = areceiver;
//        mainDB = amainDB;
//        this.datagramChannel = datagramChannel;
//        buffer = new byte[4096];
//        getCollection();
//    }
//
//    private void getCollection() {
//        try {
//            ResultSet data = mainDB.getCollection();
//            while (data.next()) {
//                receiver.add(new StudyGroup(
//                        data.getInt(1),
//                        data.getString(2),
//                        new Coordinates(data.getDouble(3), data.getInt(4)),
//                        data.getDate(5).toLocalDate().atStartOfDay().atZone(ZoneId.of("Europe/Moscow")),
//                        data.getString(7) != null ? Integer.valueOf(data.getString(7)) : null,
//                        data.getString(8) != null ?
//                                FormOfEducation.valueOf(data.getString(8)) : null,
//                        Semester.valueOf(data.getString(9)),
//                        new Person(data.getString(10),
//                                data.getDate(11).toLocalDate().atStartOfDay().atZone(ZoneId.of("Europe/Moscow")),
//                                data.getInt(12),
//                                data.getString(13),
//                                Color.valueOf(data.getString(14))),
//                        data.getString(15)));
//            }
//        }catch (SQLException throwables){
//            logger.info("SQL problems");
//        }
//    }
//
//
//    public Stack<StudyGroup> show() {
//        if (receiver.getMainCollection().size() == 0) return null;
//        else return receiver.getMainCollection();
//    }
//
//    public TypeOfAnswer add(StudyGroup aStudyGroup){
//        Integer id = mainDB.addStudyGroup(aStudyGroup);
//
//        if (id != 0) {
//            receiver.add(aStudyGroup.setId(id));
//            return TypeOfAnswer.SUCCESSFUL;
//        } else {
//            return TypeOfAnswer.DUPLICATESDETECTED;
//        }
//    }
//
//    public boolean loginUser(String aUsername , String password){
//        return mainDB.addUser(aUsername , password);
//
//    }
//
//    public boolean registerUser(String username , String password) {
//        return mainDB.addUser(username , password);
//    }
//
//    public TypeOfAnswer clear(String anUsername) {
//        TypeOfAnswer status = mainDB.clear(anUsername);
//
//        if (status.equals(TypeOfAnswer.SUCCESSFUL)) {
//            receiver.clearCollection(anUsername);
//            return TypeOfAnswer.SUCCESSFUL;
//        } else return status;
//    }
//
//    public void start(XmlFileHandler xmlFileHandler, Receiver receiver) {
//        this.xmlFileHandler = xmlFileHandler;
//        this.receiver = receiver;
//
//
//
//        try {
//            while (true) {
//                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
//                do{
//                    address = (InetSocketAddress) datagramChannel.receive(byteBuffer);
//                }while (address == null);
//                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer));
//                Request request = (Request) ois.readObject();
//                logger.info("Server received command: " + request.toString());
//                deliver.run(datagramChannel, address);
//            }
//            } catch (IOException e) {
//                logger.info("Some problem's with network!");
//            }catch (ClassNotFoundException e){
//                logger.info("Client sent outdated request!");
//            } catch (InterruptedException e){
//            ExitSaver exitSaver = new ExitSaver(xmlFileHandler, receiver);
//            exitSaver.run();
//
//        } catch (ExecutionException e) {
//                logger.info("Problem!");
//            }
//
//
//    }
//}
