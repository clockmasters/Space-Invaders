package spaceinvaders.game;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Intro extends JFrame implements ActionListener
{
    private JButton iniciar;
    private JButton sair;
    private JPanel painel1;
    private JPanel painel2;
    private JPanel painel3;
    private JPanel painel4;
    private JLabel txt1;
    private JLabel txt2;
    private JTextField nome_espaco;
    private String nome_jogador;
    
    public Intro()
    {
        this.setTitle("Space Invaders - Projeto POO");
        this.setSize(500, 300);
        this.setLayout(new GridLayout(5,1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        painel1 = new JPanel();
        painel2 = new JPanel();
        painel3 = new JPanel();
        painel4 = new JPanel();
        txt1 = new JLabel("Bem vindo ao Space Invaders da Zueira!");
        txt2 = new JLabel("Digite seu nome:");
        nome_espaco = new JTextField(15);
        iniciar = new JButton("Iniciar!");
        sair = new JButton("Sair");
        painel1.add(txt1);
        painel2.add(txt2);
        painel2.add(nome_espaco);
        iniciar.addActionListener(this);
        sair.addActionListener(this);
        painel3.add(iniciar);
        painel4.add(sair);
        this.add(painel1);
        this.add(painel2);
        this.add(painel3);
        this.add(painel4);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getActionCommand().equals("Iniciar!"))
        {
            try {
                Game space = new Game();
                space.gameLoop();;
            } catch (IOException ex) {
                Logger.getLogger(Intro.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            this.dispose();
        }
        if(e.getActionCommand().equals("Sair"))
        {
            System.out.println("Saindo do jogo...");
            System.exit(0);
            this.dispose();
        }
    }
    
}