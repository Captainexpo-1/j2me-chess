import javax.microedition.midlet.*;

import java.io.DataInputStream;
import java.io.IOException;

import javax.microedition.io.*;

public class Stockfish {

    public static String makeHTTPRequest(String url, String FEN, int depth) {
        HttpConnection conn = null;
        DataInputStream dis = null;
        StringBuffer sb = new StringBuffer();

        try {
            conn = (HttpConnection) Connector.open(url);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            //{
            //  "url": "https://stockfish.online/api/s/v2.php",
            //  "parameters": {
            //      "fen": "FEN STRING",
            //          "depth": DEPTH INT
            //      }
            //  }   
            //}


            String json = "{";
            json += "\"url\": \"https://stockfish.online/api/s/v2.php\",";
            json += "\"parameters\": {";
            json += "\"fen\": \"" + FEN + "\",";
            json += "\"depth\": " + depth;
            json += "}";
            json += "}";
            System.out.println("REQUESTING WITH:\n" + json);

            conn.openOutputStream().write(json.getBytes());
            dis = new DataInputStream(conn.openInputStream());
            int ch;
            while ((ch = dis.read()) != -1) {
                sb.append((char) ch);
            }
            System.out.println("RESPONSE:\n" + sb.toString());
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    public static String parseStockfishResponse(String response){
        String[] lines = Utils.split(response, '\n');
        System.out.println("LINES: ");

        String move = null;

        Utils.printStrArray(lines);
        for(int i = 0; i < lines.length; i++){
            String line = lines[i];
            int f = line.indexOf("\"bestmove ");
            if(f != -1){
                move = line.substring(f+"\"bestmove ".length(), f+"\"bestmove ".length()+4);
                break;
            }
        }
        return move;
    }

    public static Move getStockfishMove(ChessBoard board){
        String boardFEN = board.toFEN();
        String response = makeHTTPRequest("http://localhost:5000/proxy", boardFEN, 10);
        String move = parseStockfishResponse(response);
        
        Square s1 = new Square(move.substring(0, 2), true);
        Square s2 = new Square(move.substring(2, 4), true);

        return new Move(board, s1, s2, board.getPieceAt(s1), board.getPieceAt(s2));
    }

    public static void test() {
        ChessBoard board = new ChessBoard();
        board.initializeBoard();
        System.out.println(board.toFEN());
        Move m = getStockfishMove(board);
        System.out.println(m.toString());
    }
}
