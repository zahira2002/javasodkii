package com.console;

import com.game.classes.Echiquier;
import com.game.classes.Piece;
import com.game.tools.MoveException;
import com.game.tools.Point;

import java.util.Scanner;
public class ConsoleClass {


    private static int direction;

    public static void MachineContreMachine(Echiquier echiquier) {
        while (true) {
            echiquier.randomTurn();
            echiquier.showEchiquier();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /***
     *
     * il faut annuler les espaces dans string et mettre Tig_B
     */
    public static void HumanContreHuman( Echiquier echiquier) {
        while (true) {
            System.out.println("saisir votre Commande player"+echiquier.getTurn());
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();

            String[] trait = command.split(",");

            // echiquier.switchPlayer();
            Piece choosenPiece = echiquier.getPieceByLabel(trait[0]);
            if (choosenPiece != null && trait.length == 2) {
                System.out.println("you choose : " + choosenPiece);
                System.out.println("to make this move " + trait[1]);

                if ("a".equals(trait[1])) {
                    direction = 1;
                } else if ("b".equals(trait[1])) {
                    direction = 2;
                } else if ("r".equals(trait[1])) {
                    direction = 3;
                } else if ("l".equals(trait[1])) {
                    direction = 4;
                }



                try {

                    choosenPiece.movePiece(direction, 0);
                } catch (MoveException | NullPointerException e) {
                    System.out.println("Cant Move");
                }

            } else {
                System.out.println("Cant Find Piece Or command not correct");
            }

            echiquier.showEchiquier();
        }
    }

    public static void HumanContreMachine(Echiquier echiquier) {
        while (true) {
            if (echiquier.getTurn() == -1) {
                System.out.println("Saisir votre commande player " + echiquier.getTurn());
                Scanner scanner = new Scanner(System.in);
                String command = scanner.nextLine();

                String[] trait = command.split(",");

                Piece choosenPiece = echiquier.getPieceByLabel(trait[0]);
                if (choosenPiece != null && trait.length == 2) {
                    String move = trait[1];
                    int direction = 0;
                    if ("a".equals(move)) {
                        direction = 1;
                    } else if ("b".equals(move)) {
                        direction = 2;
                    } else if ("r".equals(move)) {
                        direction = 3;
                    } else if ("l".equals(move)) {
                        direction = 4;
                    }

                    try {
                        Point depart = choosenPiece.getPosition();
                        choosenPiece.movePiece(direction, 0);
                        Point arrivee = choosenPiece.getPosition();

                        // Enregistrer le déplacement dans la base de données
                        echiquier.enregistrerDeplacement(choosenPiece, depart, arrivee);
                    } catch (MoveException | NullPointerException e) {
                        System.out.println("Impossible de déplacer la pièce.");
                    }
                } else {
                    System.out.println("Impossible de trouver la pièce ou commande incorrecte.");
                }
            } else {
                // La machine joue
                Piece[] pieces = echiquier.getPieceByTurn().toArray(new Piece[0]);
                Piece choosenPiece = pieces[(int) (Math.random() * pieces.length)];
                int direction = (int) (Math.random() * 4) + 1;

                try {
                    Point depart = choosenPiece.getPosition();
                    choosenPiece.movePiece(direction, 0);
                    Point arrivee = choosenPiece.getPosition();

                    // Enregistrer le déplacement dans la base de données
                    echiquier.enregistrerDeplacement(choosenPiece, depart, arrivee);
                } catch (MoveException | NullPointerException e) {
                    // Si le déplacement est impossible, on en choisit un autre aléatoirement
                    while (true) {
                        choosenPiece = pieces[(int) (Math.random() * pieces.length)];
                        direction = (int) (Math.random() * 4) + 1;
                        try {
                            Point depart = choosenPiece.getPosition();
                            choosenPiece.movePiece(direction, 0);
                            Point arrivee = choosenPiece.getPosition();

                            // Enregistrer le déplacement dans la base de données
                            echiquier.enregistrerDeplacement(choosenPiece, depart, arrivee);

                            break;
                        } catch (MoveException | NullPointerException ex) {
                            // Si le déplacement est encore impossible, on continue la boucle
                        }
                    }
                }
            }
            echiquier.showEchiquier();
        }
    }


    public static void main(String[] args) {




        Echiquier echiquier = Echiquier.getInstance();


        System.out.println("mettre votre choix");
        System.out.println("1-Human VS Human");
        System.out.println("2-Human VS Machine");
        System.out.println("3-Machine VS Machine");
        System.out.println("4-exit");
        int choose=0;
        Scanner sc=new Scanner(System.in);
        choose=sc.nextInt();
        echiquier.showEchiquier();
        System.out.println("The game staring in 3 sec......\n");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        switch (choose) {
            case 1:
                HumanContreHuman(echiquier);
                break;
            case 2:
                HumanContreMachine(echiquier);
                break;
            case 3:
                MachineContreMachine(echiquier);
                break;
            case 4:
                System.exit(0);
                break;

            default:
                break;
        }

    }
}
