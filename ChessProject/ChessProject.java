import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/*
	This class can be used as a starting point for creating your Chess game project. The only piece that 
	has been coded is a white pawn...a lot done, more to do!
*/

public class ChessProject extends JFrame implements MouseListener, MouseMotionListener {
	JLayeredPane layeredPane;
	JPanel chessBoard;
	JLabel chessPiece;
	int xAdjustment;
	int yAdjustment;
	int startX;
	int startY;
	int initialX;
	int initialY;
	JPanel panels;
	JLabel pieces;

	public ChessProject() {
		Dimension boardSize = new Dimension(600, 600);

		// Use a Layered Pane for this application
		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setPreferredSize(boardSize);
		layeredPane.addMouseListener(this);
		layeredPane.addMouseMotionListener(this);

		// Add a chess board to the Layered Pane
		chessBoard = new JPanel();
		layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		chessBoard.setLayout(new GridLayout(8, 8));
		chessBoard.setPreferredSize(boardSize);
		chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);

		for (int i = 0; i < 64; i++) {
			JPanel square = new JPanel(new BorderLayout());
			chessBoard.add(square);

			int row = (i / 8) % 2;
			if (row == 0)
				square.setBackground(i % 2 == 0 ? Color.white : Color.gray);
			else
				square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
		}

		// Setting up the Initial Chess board.
		for (int i = 8; i < 16; i++) {
			pieces = new JLabel(new ImageIcon("WhitePawn.png"));
			panels = (JPanel) chessBoard.getComponent(i);
			panels.add(pieces);
		}
		pieces = new JLabel(new ImageIcon("WhiteRook.png"));
		panels = (JPanel) chessBoard.getComponent(0);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
		panels = (JPanel) chessBoard.getComponent(1);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteKnight.png"));
		panels = (JPanel) chessBoard.getComponent(6);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteBishup.png"));
		panels = (JPanel) chessBoard.getComponent(2);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteBishup.png"));
		panels = (JPanel) chessBoard.getComponent(5);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteKing.png"));
		panels = (JPanel) chessBoard.getComponent(3);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
		panels = (JPanel) chessBoard.getComponent(4);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("WhiteRook.png"));
		panels = (JPanel) chessBoard.getComponent(7);
		panels.add(pieces);
		for (int i = 48; i < 56; i++) {
			pieces = new JLabel(new ImageIcon("BlackPawn.png"));
			panels = (JPanel) chessBoard.getComponent(i);
			panels.add(pieces);
		}
		pieces = new JLabel(new ImageIcon("BlackRook.png"));
		panels = (JPanel) chessBoard.getComponent(56);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackKnight.png"));
		panels = (JPanel) chessBoard.getComponent(57);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackKnight.png"));
		panels = (JPanel) chessBoard.getComponent(62);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackBishup.png"));
		panels = (JPanel) chessBoard.getComponent(58);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackBishup.png"));
		panels = (JPanel) chessBoard.getComponent(61);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackKing.png"));
		panels = (JPanel) chessBoard.getComponent(59);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackQueen.png"));
		panels = (JPanel) chessBoard.getComponent(60);
		panels.add(pieces);
		pieces = new JLabel(new ImageIcon("BlackRook.png"));
		panels = (JPanel) chessBoard.getComponent(63);
		panels.add(pieces);
	}

	/*
	 * This method checks if there is a piece present on a particular square.
	 */
	private Boolean piecePresent(int x, int y) {
		Component c = chessBoard.findComponentAt(x, y);
		if (c instanceof JPanel) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * This is a method to check if a piece is a Black piece.
	 */
	private Boolean checkWhiteOponent(int newX, int newY) {
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel) c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if (((tmp1.contains("Black")))) {
			oponent = true;
		} else {
			oponent = false;
		}
		return oponent;
	}

	private Boolean checkBlackOponent(int newX, int newY) {
		Boolean oponent;
		Component c1 = chessBoard.findComponentAt(newX, newY);
		JLabel awaitingPiece = (JLabel) c1;
		String tmp1 = awaitingPiece.getIcon().toString();
		if (((tmp1.contains("White")))) {
			oponent = true;
		} else {
			oponent = false;
		}
		return oponent;

	}

	/*
	 * This method is called when we press the Mouse. So we need to find out what
	 * piece we have selected. We may also not have selected a piece!
	 */
	public void mousePressed(MouseEvent e) {
		chessPiece = null;
		Component c = chessBoard.findComponentAt(e.getX(), e.getY());
		if (c instanceof JPanel)
			return;

		Point parentLocation = c.getParent().getLocation();
		xAdjustment = parentLocation.x - e.getX();
		yAdjustment = parentLocation.y - e.getY();
		chessPiece = (JLabel) c;
		initialX = e.getX();
		initialY = e.getY();
		startX = (e.getX() / 75);
		startY = (e.getY() / 75);
		chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
		chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
		layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
	}

	public void mouseDragged(MouseEvent me) {
		if (chessPiece == null)
			return;
		chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
	}

	/*
	 * This method is used when the Mouse is released...we need to make sure the
	 * move was valid before putting the piece back on the board.
	 */
	public void mouseReleased(MouseEvent e) {
		if (chessPiece == null)
			return;

		chessPiece.setVisible(false);
		Boolean success = false;
		Boolean bsuccess = false;

		Component c = chessBoard.findComponentAt(e.getX(), e.getY());
		String tmp = chessPiece.getIcon().toString();
		String pieceName = tmp.substring(0, (tmp.length() - 4));
		Boolean validMove = false;

		/*
		 * The only piece that has been enabled to move is a White Pawn...but we should
		 * really have this is a separate method somewhere...how would this work.
		 * 
		 * So a Pawn is able to move two squares forward one its first go but only one
		 * square after that. The Pawn is the only piece that cannot move backwards in
		 * chess...so be careful when committing a pawn forward. A Pawn is able to take
		 * any of the opponent’s pieces but they have to be one    forward and one
		 * square over, i.e. in a diagonal direction from the Pawns original position.
		 * If a Pawn makes it to the top of the other side, the Pawn can turn into any
		 * other piece, for demonstration purposes the Pawn here turns into a Queen.
		 */

		int landingX = (e.getX() / 75);
		int landingY = (e.getY() / 75);
		int xMovement = Math.abs((e.getX() / 75) - startX);
		int yMovement = Math.abs((e.getY() / 75) - startY);
		System.out.println("***************");
		System.out.println("The piece that has been moved is :" + pieceName);
		System.out.println("The starting coordinates are :" + "(" + startX + "," + startY + ")");
		System.out.println("the xMovement is" + xMovement);
		System.out.println("the yMovement is" + yMovement);
		System.out.println("the landing coordinates are :" + "(" + landingX + "," + landingY + ")");
		System.out.println("***************");

		if (pieceName.equals("BlackQueen")) {
			validMove = true;
		} else if (pieceName.equals("BlackPawn")) {
			if (startY == 6) {
				/*
				 * if the pawn is making its first move..... the pawn can either move one square
				 * or two squares in the Y Direction as long as we are moving UP the board and
				 * also there is no movement in the x direction
				 */
				if (((yMovement == 1) || (yMovement == 2)) && (startY > landingY) && (xMovement == 0)) {
					if (yMovement == 2) {
						if ((!piecePresent(e.getX(), e.getY())) && (!piecePresent(e.getX(), (e.getY() + 75)))) {
							validMove = true;
						}
					} else {
						if (!piecePresent(e.getX(), e.getY())) {
							validMove = true;
						}
					}
				} else if ((yMovement == 1) && (startY > landingY) && (xMovement == 1)) {
					if (piecePresent(e.getX(), e.getY())) {
						if (checkBlackOponent(e.getX(), e.getY())) {
							validMove = true;
						}
					}
				}
			} else {// this is where the pawn is making all subsequent moves
				if (((yMovement == 1)) && (startY > landingY) && (xMovement == 0)) {
					if (!piecePresent(e.getX(), e.getY())) {
						validMove = true;
						if (startY == 1) {
							bsuccess = true;
						}
					}
				} else if ((yMovement == 1) && (startY > landingY) && (xMovement == 1)) {
					if (piecePresent(e.getX(), e.getY())) {
						if (checkBlackOponent(e.getX(), e.getY())) {
							validMove = true;
							if (startY == 1) {
								bsuccess = true;
							}
						}
					}
				}
			}
		}
		/**********************************************************************/
		/**********************************************************************/
		/****************************
		 * WHITE PAWN
		 ******************************************/

		if (pieceName.equals("WhiteQueen")) {
			validMove = true;
		} else if (pieceName.equals("WhitePawn")) {
			if (startY == 1) {
				/*
				 * if the pawn is making its first move..... the pawn can either move one square
				 * or two squares in the Y Direction as long as we are moving UP the board and
				 * also there is no movement in the x direction
				 */
				if (((yMovement == 1) || (yMovement == 2)) && (startY < landingY) && (xMovement == 0)) {
					if (yMovement == 2) {
						if ((!piecePresent(e.getX(), e.getY())) && (!piecePresent(e.getX(), (e.getY() - 75)))) {
							validMove = true;
						}
					} else {
						if (!piecePresent(e.getX(), e.getY())) {
							validMove = true;
						}
					}
				} else if ((yMovement == 1) && (startY < landingY) && (xMovement == 1)) {
					if (piecePresent(e.getX(), e.getY())) {
						if (checkWhiteOponent(e.getX(), e.getY())) {
							validMove = true;
						}
					}
				}
			} else {// this is where the pawn is making all subsequent moves
				if (((yMovement == 1)) && (startY < landingY) && (xMovement == 0)) {
					if (!piecePresent(e.getX(), e.getY())) {
						validMove = true;
					}
					if (startY == 6) {
						success = true;
					}
				} else if ((yMovement == 1) && (startY < landingY) && (xMovement == 1)) {
					if (piecePresent(e.getX(), e.getY())) {
						if (checkWhiteOponent(e.getX(), e.getY())) {
							validMove = true;
							if (startY == 6) {
								success = true;
							}
						}
					}
				}
			}
		}
		/**********************************************************************/
		/**********************************************************************/
		/****************************
		 * kNight
		 ******************************************/
		if (pieceName.contains("Knight")) {
			if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || landingY > 7)) {
				validMove = false;
			} else {
				if (((landingX == startX + 1) && (landingY == startY + 2))
						|| ((landingX == startX - 1) && (landingY == startY + 2))
						|| ((landingX == startX + 2) && (landingY == startY + 1))
						|| ((landingX == startX - 2) && (landingY == startY + 1))
						|| ((landingX == startX + 1) && (landingY == startY - 2))
						|| ((landingX == startX - 1) && (landingY == startY - 2))
						|| ((landingX == startX + 2) && (landingY == startY - 1))
						|| ((landingX == startX - 2) && (landingY == startY - 1))) {

					if (piecePresent(e.getX(), (e.getY()))) {
						if (pieceName.contains("White")) {
							if (checkWhiteOponent(e.getX(), e.getY())) {
								validMove = true;
							} else {
								validMove = false;
							}
						} else {
							if (checkBlackOponent(e.getX(), e.getY())) {
								validMove = true;
							} else {
								validMove = false;
							}
						}
					} else {
						validMove = true;
					}
				} else {
					validMove = false;
				}
			}

		}
		/**********************************************************************/
		/**********************************************************************/
		/****************************
		 * Bishop
		 ******************************************/
		if (pieceName.contains("Bishup")) {
			Boolean inTheWay = false;
			int distance = Math.abs(startX - landingX);
			if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
				validMove = false;
			} else {
				validMove = true;
				if (Math.abs(startX - landingX) == Math.abs(startY - landingY)) {
					if ((startX - landingX < 0) && (startY - landingY < 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX + (i * 75)), (initialY + (i * 75)))) {
								inTheWay = true;
							}
						}
					} else if ((startX - landingX < 0) && (startY - landingY > 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX + (i * 75)), (initialY - (i * 75)))) {
								inTheWay = true;
							}
						}
					}

					else if ((startX - landingX > 0) && (startY - landingY > 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX - (i * 75)), (initialY + (i * 75)))) {
								inTheWay = true;
							}
						}

					} else if ((startX - landingX > 0) && (startY - landingY < 0)) {
						for (int i = 0; i < distance; i++) {
							if (piecePresent((initialX - (i * 75)), (initialY + (i * 75)))) {
								inTheWay = true;
							}
						}
					}
					if (inTheWay) {
						validMove = false;
					} else {
						if (piecePresent(e.getX(), (e.getY()))) {
							if (pieceName.contains("White")) {
								if (checkWhiteOponent(e.getX(), e.getY())) {
									validMove = true;
								}

								else {
									validMove = false;
								}
							}

							else {
								if (checkBlackOponent(e.getX(), e.getY())) {
									validMove = true;

								} else {
									validMove = false;
								}
							}
						}

						else {
							validMove = true;
						}
					}
				} else {
					validMove = false;
				}
			}
		}
		/**********************************************************************/
		/**********************************************************************/
		/****************************
		 * ROOK (CASTLE)
		 ******************************************/
		if (pieceName.contains("Rook")) {
			Boolean intheway = false;
			if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
				validMove = false;
			} else {
				if (((Math.abs(startX - landingX) != 0) && (Math.abs(startY - landingY) == 0))
						|| ((Math.abs(startX - landingX) == 0) && (Math.abs(landingY - startY) != 0))) {
					if (Math.abs(startX - landingX) != 0) {
						// int xMovement = Math.abs(startX-landingX);
						if (startX - landingX > 0) {
							for (int i = 0; i < xMovement; i++) {
								if (piecePresent(initialX - (i * 75), e.getY())) {
									intheway = true;
									break;
								} else {
									intheway = false;
								}
							}
						} else {
							for (int i = 0; i < xMovement; i++) {
								if (piecePresent(initialX + (i * 75), e.getY())) {
									intheway = true;
									break;
								} else {
									intheway = false;
								}
							}
						}
					} else {
						// int yMovement = Math.abs(startY-landingY);
						if (startY - landingY > 0) {
							for (int i = 0; i < yMovement; i++) {
								if (piecePresent(e.getX(), initialY - (i * 75))) {
									intheway = true;
									break;
								} else {
									intheway = false;
								}
							}
						} else {
							for (int i = 0; i < yMovement; i++) {
								if (piecePresent(e.getX(), initialY + (i * 75))) {
									intheway = true;
									break;
								} else {
									intheway = false;
								}
							}
						}
					}
					if (intheway) {
						validMove = false;
					} else {
						if (piecePresent(e.getX(), (e.getY()))) {
							if (pieceName.contains("White")) {
								if (checkWhiteOponent(e.getX(), e.getY())) {
									validMove = true;
								} else {
									validMove = false;
								}
							} else {
								if (checkBlackOponent(e.getX(), e.getY())) {
									validMove = true;
								} else {
									validMove = false;
								}
							}
						} else {
							validMove = true;
						}
					}
				} else {
					validMove = false;
				}
			}
		}
		/**********************************************************************/
		/**********************************************************************/
		/****************************
		 * Queen
		 ******************************************/
		if (pieceName.contains("Queen")) {
			int xCordinate = 0;
			int yCordinate = 0;
            Boolean inTheWay = false;
            int distance = Math.abs(startX - landingX); // startpos - landing pos
            if (((landingX < 0) || (landingX > 7)) || ((landingY < 0) || (landingY > 7))) {
                // if the x landing is less than 0 or greater than 7 or y landing is less than 0
                // or greater than 7
                // basically if x and is greater than 7 or less than 0 invalid move dimenions of
                // board is (0,7),(7,0) ie 8 x 8
                validMove = false;
                // invalid move
            }
            else {
                validMove = true;
            }
            if (Math.abs(xCordinate) == Math.abs(yCordinate)) {
                // if start pos is 0 - landing pos say for example 3 math.abs returns position
                // to be 3 and same for y co-ordinate
                // so this essentially determines were the piece will be placed on the board
                /*
                 * Here we have 4 conditions to determine the direction of the diagnol that the
                 * bishop is intedning to move along
                 */
                if ((xCordinate < 0) && (yCordinate < 0)) { // if x and y cordinates less than 0 how can they be less
                                                            // than 0? invalid move?
                    for (int i = 0; i < distance; i++) { // i = 0 i < the x cordinate distance incrment i

                        if (piecePresent((initialX + (i * 75)), (initialY + (i * 75)))) { // dont understand i *75??
                            // if there is a piece present inThe Way is true
                            inTheWay = true;
                        }
                    }
                }
                else if ((xCordinate < 0) && (yCordinate > 0)) {
                    for (int i = 0; i < distance; i++) {
                        if (piecePresent((initialX + (i * 75)), (initialY - (i * 75)))) {
                            inTheWay = true;
                        }
                    }
                }
                else if ((xCordinate > 0) && (yCordinate > 0)) {
                    for (int i = 0; i < distance; i++) {
                        if (piecePresent((initialX - (i * 75)), (initialY - (i * 75)))) {
                            inTheWay = true;
                        }
                    }
                }
                else if ((xCordinate > 0) && (yCordinate < 0)) {
                    for (int i = 0; i < distance; i++) {
                        if (piecePresent((initialX - (i * 75)), (initialY + (i * 75)))) {
                            inTheWay = true;
                        }
                    }
                }
                if (inTheWay) { // if in the way is true invalid move
                    validMove = false;
                }
                else { // if piece present at specified co-ordinates and if piece contains white valid
                       // move else invalid move
                    if (piecePresent(e.getX(), (e.getY()))) {
                        if (pieceName.contains("White")) {
                            if (checkWhiteOponent(e.getX(), e.getY())) {
                                validMove = true;
                            }
                            else {
                                validMove = false;
                            }
                        }
                        else { // else if black opponent valid move true else false
                            if (checkBlackOponent(e.getX(), e.getY())) {
                                validMove = true;
                            }
                            else {
                                validMove = false;
                            }
                        }
                    }
                    // else true else false why do we need these 2 elses??
                   else {
                        validMove = true;
                    }
                }
            } else {
                validMove = false;
            }
            if (((Math.abs(xCordinate) != 0) && (Math.abs(yCordinate) == 0))  || ((Math.abs(xCordinate) == 0) && (Math.abs(yCordinate) != 0))) {
                // if xcordinate != 0 and y =0 or x = 0 and y !=0
                /********* 4 Different set of moves for rook ***********/
                if (Math.abs(xCordinate) != 0) { // if x doesnt equal zero do this
                    xMovement = Math.abs(xCordinate); // new variable
                    if (xCordinate > 0) { // if x greater than 0
                        for (int i = 0; i < xMovement; i++) { // goes into this as x cordinate is not equal to and
                                                              // greater than 0
                            if (piecePresent(initialX - (i * 75), e.getY())) { // if theres a piece present
                                inTheWay = true; // in the way
                                break;
                            } else {
                                inTheWay = false; // false
                            }
                        }
                    }
                    else { // else if x cordinate does equal to zero or is less than 0
                        for (int i = 0; i < xMovement; i++) {
                            if (piecePresent(initialX + (i * 75), e.getY())) {
                                inTheWay = true;
                                break;
                            } else {
                                inTheWay = false;
                            }
                        }
                    }
                }
                // same scenario for y cordinate
                else {
                    yMovement = Math.abs(yCordinate);
                    if (yCordinate > 0) {
                        for (int i = 0; i < yMovement; i++) {
                            if (piecePresent(e.getX(), initialY - (i * 75))) {
                                inTheWay = true;
                                break;
                            } else {
                                inTheWay = false;
                            }
                        }
                    }
                    else {
                        for (int i = 0; i < yMovement; i++) {
                            if (piecePresent(e.getX(), initialY + (i * 75))) {
                                inTheWay = true;
                                break;
                            } else {
                                inTheWay = false;
                            }
                        }
                    }
                }
                // if in the way true invalid move
                if (inTheWay) {
                    validMove = false;
                }
                // so we cant take our own piece
                else {
                    if (piecePresent(e.getX(), (e.getY()))) {
                        if (pieceName.contains("White")) {
                            if (checkWhiteOponent(e.getX(), e.getY())) {
                                validMove = true;
                            }
                            else {
                                validMove = false;
                            }
                        }
                        else {
                            if (checkBlackOponent(e.getX(), e.getY())) {
                                validMove = true;
                            }
                            else {
                                validMove = false;
                            }
                        }
                    }
                    else {
                        validMove = true;
                    }
                }
            }
        }


		/*
		 * if(pieceName.equals("WhitePawn")){ if(startY == 1) { if((startX ==
		 * (e.getX()/75))&&((((e.getY()/75)-startY)==1)||((e.getY()/75)-startY)==2)) {
		 * if((((e.getY()/75)-startY)==2)){ if((!piecePresent(e.getX(),
		 * (e.getY())))&&(!piecePresent(e.getX(), (e.getY()+75)))){ validMove = true; }
		 * else{ validMove = false; } } else{ if((!piecePresent(e.getX(), (e.getY()))))
		 * { validMove = true; } else{ validMove = false; } } } else{ validMove = false;
		 * } } else{ int newY = e.getY()/75; int newX = e.getX()/75; if((startX-1
		 * >=0)||(startX +1 <=7)) { if((piecePresent(e.getX(), (e.getY())))&&((((newX ==
		 * (startX+1)&&(startX+1<=7)))||((newX == (startX-1))&&(startX-1 >=0))))) {
		 * if(checkWhiteOponent(e.getX(), e.getY())){ validMove = true; if(startY == 6){
		 * success = true; } } else{ validMove = false; } } else{
		 * if(!piecePresent(e.getX(), (e.getY()))){ if((startX ==
		 * (e.getX()/75))&&((e.getY()/75)-startY)==1){ if(startY == 6){ success = true;
		 * } validMove = true; } else{ validMove = false; } } else{ validMove = false; }
		 * } } else{ validMove = false; } } }
		 */
		if (!validMove) {
			int location = 0;
			if (startY == 0) {
				location = startX;
			} else {
				location = (startY * 8) + startX;
			}
			String pieceLocation = pieceName + ".png";
			pieces = new JLabel(new ImageIcon(pieceLocation));
			panels = (JPanel) chessBoard.getComponent(location);
			panels.add(pieces);
		} else {
			if (success) {
				int location = 56 + (e.getX() / 75);
				if (c instanceof JLabel) {
					Container parent = c.getParent();
					parent.remove(0);
					pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
					parent = (JPanel) chessBoard.getComponent(location);
					parent.add(pieces);
				} else {
					Container parent = (Container) c;
					pieces = new JLabel(new ImageIcon("WhiteQueen.png"));
					parent = (JPanel) chessBoard.getComponent(location);
					parent.add(pieces);
				}
			} else if (bsuccess) {
				int location = 0 + (e.getX() / 75);
				if (c instanceof JLabel) {
					Container parent = c.getParent();
					parent.remove(0);
					pieces = new JLabel(new ImageIcon("BlackQueen.png"));
					parent = (JPanel) chessBoard.getComponent(location);
					parent.add(pieces);
				} else {
					Container parent = (Container) c;
					pieces = new JLabel(new ImageIcon("BlackQueen.png"));
					parent = (JPanel) chessBoard.getComponent(location);
					parent.add(pieces);
				}
			}

			else {
				if (c instanceof JLabel) {
					Container parent = c.getParent();
					parent.remove(0);
					parent.add(chessPiece);
				} else {
					Container parent = (Container) c;
					parent.add(chessPiece);
				}
				chessPiece.setVisible(true);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	/*
	 * Main method that gets the ball moving.
	 */
	public static void main(String[] args) {
		JFrame frame = new ChessProject();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
