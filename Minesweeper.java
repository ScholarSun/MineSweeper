
//Minesweeper - Scholar Sun - January 21 2015
//Import
import java.awt.*;
import javax.swing.*;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.event.MouseListener;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Minesweeper {
	//Static variables
	static int col, row, cols, rows;
	static int score;
	static JButton[][] btn;
	static JFrame board, menu, page;
	static int randomNum, randomNum2;
	static String bombcord[] = new String[9];
	static String[] bcs;
	static ImageIcon mine, one, two, three, four, five, six, seven, eight, flag;
	static JPanel panel, menupanel, pagepanel;
	static boolean scoreflag = false;
	static boolean flagend;
	static int Numbers[][] = new int[9][9];
	static boolean visited[][] = new boolean[9][9];
	static boolean flagged[][] = new boolean[9][9];
	static int counter = 0, flags = 0;
	
	//Main method
	public static void main(String[] args) {
		//Creates Buttons
		JButton StartButton;
		JButton InstructionButton;
		
		StartButton = new JButton();
		StartButton.setSize(new Dimension(250, 30));
		
		InstructionButton = new JButton();
		InstructionButton.setSize(new Dimension(250, 30));
		
		//Menu Images
		ImageIcon start, instructions;
		start = new ImageIcon("src/start.png");
		instructions = new ImageIcon("src/instructions.png");

		StartButton.setIcon(start);
		InstructionButton.setIcon(instructions);

		// ----------JFrame Board----------
		// JFrame settings
		board = new JFrame();
		board.setVisible(false);
		board.setTitle("Minesweeper");
		board.setSize(800, 800);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ----------JFrame Instructions Page----------
		page = new JFrame();
		page.setVisible(false);
		page.setTitle("Minesweeper");
		page.setSize(800, 800);
		page.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// makes it closable
		page.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// draws the instructions
		page.getContentPane().add(new JLabel(new ImageIcon("src/instrucpage.png")));
		page.validate();
		
		// ----------JFrame Main Menu----------
		//Main menu settings
		menu = new JFrame();
		menu.setVisible(false);
		menu.setTitle("Menu");
		menu.setSize(800, 800);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// new JPanel for the menu
		menupanel = new JPanel();
		
		// Mouse listener for Start button
		StartButton.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
				
				// greets you when you run
				System.out.println("Game Start!\nGood luck!");
				board.setVisible(true);
				
				// makes the other pages invisible
				menu.setVisible(false);
				page.setVisible(false);
			}
		});
		
		// Mouse listener for Instruction button
		InstructionButton.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
				page.setVisible(true);
			}
		});
		
		// Sets the layout for the main menu's JPanel
		menupanel.setLayout(new BoxLayout(menupanel, BoxLayout.PAGE_AXIS));
		menupanel.add(StartButton);
		menupanel.add(Box.createRigidArea(new Dimension(200, 200)));
		menupanel.add(InstructionButton);
		menupanel.setBorder(BorderFactory.createEmptyBorder(400, 60, 10, 10));

		// background title
		menu.getContentPane().add(new JLabel(new ImageIcon("src/background.png")));
		menu.validate();
		menu.setVisible(true); 
		menu.getContentPane().add(menupanel);
		menu.validate();

		// ----------JPanel----------
		// Adds panel to board
		panel = new JPanel();
		board.getContentPane().add(panel);
		
		// declares the size, how many rows or cols
		rows = 9;
		cols = 9;
		// ----------JButtonArray----------
		// new array of buttons, 9 by 9
		btn = new JButton[rows][cols];
		
		// Sets grid layout
		GridLayout boxes = new GridLayout(rows, cols, 4, 4);
		
		// sets the panel with this layout
		panel.setLayout(boxes);
		// ----------Mine Placements----------
		// Generates the mine co-ordinates
		for (int x = 0; x < 9; x++) {
			// Random number
			Random rand = new Random();
			String k = GenerateRandomNumber();
			
			// if the array of current bomb does not include it self add it in
			if (!Arrays.asList(bombcord).contains(k)) {
				bombcord[x] = k;
			}
			
			// if the array has this element already make another one
			else {
				String g = GenerateRandomNumber();
				// while there is a repeat, make another bomb
				while (Arrays.asList(bombcord).contains(g)) {
					g = GenerateRandomNumber();
				}
				bombcord[x] = g;
			}
		}

		// ----------Rename array----------
		bcs = bombcord;
		numbersettingname();

		// ----------Icons Used----------
		mine = new ImageIcon("src/mine.png");
		one = new ImageIcon("src/one.png");
		two = new ImageIcon("src/two.png");
		three = new ImageIcon("src/three.png");
		four = new ImageIcon("src/four.png");
		five = new ImageIcon("src/five.png");
		six = new ImageIcon("src/six.png");
		seven = new ImageIcon("src/seven.png");
		eight = new ImageIcon("src/eight.png");
		flag = new ImageIcon("src/flag.png");

		// ----------What Happens If You Click----------
		// This part adds an action listner to each of the buttons on the board
		for (row = 0; row < rows; row++) {

			for (col = 0; col < cols; col++) {
				// adds mouse listener to each individual button
				btn[row][col] = new JButton(new Integer(col).toString() + new Integer(row).toString());
				btn[row][col].addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {
					}

					public void mouseEntered(MouseEvent e) {
					}

					public void mouseExited(MouseEvent e) {
					}

					public void mousePressed(MouseEvent e) {
					}

					public void mouseClicked(MouseEvent e) {
						//Gets source of action
						JButton selected = (JButton) e.getSource();
						
						//extracts and gets co-ordinates
						String btncord = selected.getText().trim();

						// Run if button hasn't been clicked yet
						if (btncord.length() > 0) {
							boolean stop = false;
							String selcol = btncord.substring(0, 1);
							int selcolint = Integer.parseInt(selcol);
							
							// selected row number
							String selrow = btncord.substring(1, 2);
							int selrowint = Integer.parseInt(selrow);

							// Clears the button to be painted/repainted
							selected.setIcon(null);

							// flag placement
							if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK
									&& !visited[selrowint][selcolint]) {
								selected.setIcon(flag);
								flags++;
								flagged[selrowint][selcolint] = true;

								for (int q = 0; q < 9; q++) {
									if (btncord.equals(bcs[q])) {
										//Limits flag number
										counter = counter + 1;
									}
								}

							} else {
								for (int k = 0; k < 9; k++) {
									// Checks if bomb = click co-ordinates
									if (btncord.equals(bcs[k]) && (e.getModifiers()
											& InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
										// sets the score
										scoreflag = false;
										
										// Clears the board from buttons
										ClearBoard(btn);
										
										// Boom First
										System.out.println("BOOM");
										System.out.println("YOU LOSE!");
										
										// Delay after the bomb is clicked
										try {
											Thread.sleep(100);
										} catch (Exception ex) {
											System.out.println(ex.toString());
										}

										// sets the bomb icons & displays
										for (int j = 0; j < 9; j++) {
											String Allbomb = bcs[j];
											char d1 = Allbomb.charAt(0);
											char d2 = Allbomb.charAt(1);
											int bombrow = Character.getNumericValue(d1);
											int bombcol = Character.getNumericValue(d2);
											btn[bombcol][bombrow].setIcon(mine);
										}
										// disables the screen when clicked
										disablebuttons(btn);
										stop = true;
										break;
									}
								}

								// if not a bomb and clicked this runs
								if (!stop) {
									// clears the square after clicked
									selected.setText("");
									scoreflag = true;

									if (flagged[selrowint][selcolint] == true)
										flags--;

									visited[selrowint][selcolint] = true;

									if ((Numbers[selrowint][selcolint]) == 0) {
										scoreflag = false;
										
										// BFS graph search
										WannabeQueue xcord = new WannabeQueue(81);
										WannabeQueue ycord = new WannabeQueue(81);
										JButton d;

										xcord.push(selrowint);
										ycord.push(selcolint);

										while (!xcord.isEmpty()) {
											int x = xcord.front();
											int y = ycord.front();
											d = btn[x][y];
											score += 10;
											if (flagged[x][y] == true)
												flags--;
											
											// Display button x,y;
											if (Numbers[x][y] == 0) {
												d.setIcon(null);
												d.setText("");
											}
											if ((Numbers[x][y]) == 1) {
												d.setIcon(one);
											}
											if ((Numbers[x][y]) == 2) {
												d.setIcon(two);
											}
											if ((Numbers[x][y]) == 3) {
												d.setIcon(three);
											}
											if ((Numbers[x][y]) == 4) {
												d.setIcon(four);
											}
											if ((Numbers[x][y]) == 5) {
												d.setIcon(five);
											}
											if ((Numbers[x][y]) == 6) {
												d.setIcon(six);
											}
											if ((Numbers[x][y]) == 7) {
												d.setIcon(seven);
											}
											if ((Numbers[x][y]) == 8) {
												d.setIcon(eight);
											}
											// End dislay
											// BFS Search - if you find an
											// untaken spot and your current
											// spot is 0, add it to the queue
											if (Numbers[x][y] == 0) {
												if (x - 1 > -1 && y - 1 > -1) {
													if (visited[x - 1][y - 1] == false) {
														visited[x - 1][y - 1] = true;
														xcord.push(x - 1);
														ycord.push(y - 1);
													}
												}
												if (x - 1 > -1) {
													if (visited[x - 1][y] == false) {
														visited[x - 1][y] = true;
														xcord.push(x - 1);
														ycord.push(y);
													}
												}
												if (x - 1 > -1 && y + 1 < 9) {
													if (visited[x - 1][y + 1] == false) {
														visited[x - 1][y + 1] = true;
														xcord.push(x - 1);
														ycord.push(y + 1);
													}
												}
												if (y + 1 < 9) {
													if (visited[x][y + 1] == false) {
														visited[x][y + 1] = true;
														xcord.push(x);
														ycord.push(y + 1);
													}
												}
												if (y - 1 > -1) {
													if (visited[x][y - 1] == false) {
														visited[x][y - 1] = true;
														xcord.push(x);
														ycord.push(y - 1);
													}
												}
												if (x + 1 < 9 && y - 1 > -1) {
													if (visited[x + 1][y - 1] == false) {
														visited[x + 1][y - 1] = true;
														xcord.push(x + 1);
														ycord.push(y - 1);
													}
												}
												if (x + 1 < 9) {
													if (visited[x + 1][y] == false) {
														visited[x + 1][y] = true;
														xcord.push(x + 1);
														ycord.push(y);
													}
												}
												if (x + 1 < 9 && y + 1 < 9) {
													if (visited[x + 1][y + 1] == false) {
														visited[x + 1][y + 1] = true;
														xcord.push(x + 1);
														ycord.push(y + 1);
													}
												}
											}
											xcord.pop();
											ycord.pop();
										}
									}
									if ((Numbers[selrowint][selcolint]) == 1) {
										selected.setIcon(one);
									}
									if ((Numbers[selrowint][selcolint]) == 2) {
										selected.setIcon(two);
									}
									if ((Numbers[selrowint][selcolint]) == 3) {
										selected.setIcon(three);
									}
									if ((Numbers[selrowint][selcolint]) == 4) {
										selected.setIcon(four);
									}
									if ((Numbers[selrowint][selcolint]) == 5) {
										selected.setIcon(five);
									}
									if ((Numbers[selrowint][selcolint]) == 6) {
										selected.setIcon(six);
									}
									if ((Numbers[selrowint][selcolint]) == 7) {
										selected.setIcon(seven);
									}
									if ((Numbers[selrowint][selcolint]) == 8) {
										selected.setIcon(eight);
									}
								}
								// adds ten to each clear square
								if (scoreflag == true) {
									score = score + 10;
								}
								// prints score
								System.out.println("Your score=" + score);

							}
							//Checks for victory
							if (counter == 9 && flags == 9) {
								System.out.println("Good Job, Congrats you won!");
								disablebuttons(btn);
							}
						}
					}
				}

				);
				// ----------Adds Buttons----------
				panel.add(btn[row][col]);
			}
		}
		// prints the things on to the board
		board.validate();
	} // main method

	// ----------METHODS----------
	// use to generate random strings
	static String GenerateRandomNumber() {
		Random rand = new Random();
		
		// generates two random ints, from 0 to 8
		randomNum = rand.nextInt((8 - 0) + 1) + 0;
		randomNum2 = rand.nextInt((8 - 0) + 1) + 0;
		
		// converts the two random ints into strings, then places them next to
		// each other to look like co-ordinates ie. "34" or "88"
		String k = (new Integer(randomNum).toString() + new Integer(randomNum2).toString());
		return k;
	}

	// used to set all the boards to blanks
	static void ClearBoard(JButton btn[][]) {
		// row 0-8
		for (int j = 0; j < 9; j++) {
			// col 0-8
			for (int k = 0; k < 9; k++) {
				// sets all buttons to blanks
				btn[j][k].setText("");
			}
		}
	}

	// used to set and give a button of numbers, which is the number of mines
	// around that block
	static void numbersettingname() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Numbers[i][j] = 0;
				visited[i][j] = flagged[i][j] = false;
			}
		}

		for (int h = 0; h < 9; h++) {
			// all bomb coridnates to int
			String bmbcord = bcs[h];
			
			//bomb Col Number
			String bmbcordcolstr = bmbcord.substring(0, 1);
			int bmbcordcolint = Integer.parseInt(bmbcordcolstr);
			
			// //bomb Row Number
			String bmbcordrowstr = bmbcord.substring(1, 2);
			int bmbcordrowint = Integer.parseInt(bmbcordrowstr);
			
			// //Generates mines number around mine
			// Checks stuff around mines
			if (bmbcordrowint - 1 > -1 && bmbcordcolint - 1 > -1)
				Numbers[bmbcordrowint - 1][bmbcordcolint - 1]++;
			if (bmbcordrowint - 1 > -1)
				Numbers[bmbcordrowint - 1][bmbcordcolint]++;
			if (bmbcordrowint - 1 > -1 && bmbcordcolint + 1 < 9)
				Numbers[bmbcordrowint - 1][bmbcordcolint + 1]++;
			if (bmbcordcolint - 1 > -1)
				Numbers[bmbcordrowint][bmbcordcolint - 1]++;
			if (bmbcordcolint + 1 < 9)
				Numbers[bmbcordrowint][bmbcordcolint + 1]++;
			if (bmbcordrowint + 1 < 9 && bmbcordcolint - 1 > -1)
				Numbers[bmbcordrowint + 1][bmbcordcolint - 1]++;
			if (bmbcordrowint + 1 < 9)
				Numbers[bmbcordrowint + 1][bmbcordcolint]++;
			if (bmbcordrowint + 1 < 9 && bmbcordcolint + 1 < 9)
				Numbers[bmbcordrowint + 1][bmbcordcolint + 1]++;

		}

	}

	static void disablebuttons(JButton btn[][]) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				// disables buttons
				btn[i][j].setEnabled(false);
			}
		}
	}

	// ----------loadImage method----------

	public static Image loadImage(String name) // Loads image from file
	{
		Image img = null;
		try {
			img = ImageIO.read(new File(name));
		} catch (IOException e) {
		}
		return img;
	}

	// ----------END OF METHODS----------
}
