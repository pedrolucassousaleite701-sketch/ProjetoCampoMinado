/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author PedroLeite
 */
public class Jogo2 extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Jogo2.class.getName());

    
    
    
     JButton [][] btnCampo = new JButton[10][10];
    
    //MATRIZ PARA GUARDA AS BOMBAS - true p/ bomba, false p/ numero
    boolean [][] bombas = new boolean [10][10];
    
    // MAREIZ PARA GUARDAR OS CAMPOS QUE FOREM ABERTOS
    boolean [][] abertos = new boolean[10][10];
    
    int quantidadeBombas = 15;
    int quantidadeCasasAbertas = 0;
    boolean jogoEncerrado= false;
       
    int segundosPassados = 0;
    Timer cronometro;
    private int segundoPassados;
    
    
    
    public Jogo2() {
        initComponents();
        CriarTabuleiro();
        
        painelCampo.setPreferredSize(new Dimension(900,700));
        
    }

    
    
    
     public void CriarTabuleiro(){
        
        painelCampo.setLayout(new GridLayout(10,10,2,2));
        
        for(int coluna=0;coluna<=9;coluna++){
            for(int linha=0;linha<=9;linha++){
                
                // váriavel botão para guarda os dados previsorios
                JButton botao = new JButton();
                botao.setFont(new Font("Arial",Font.BOLD,16));
                botao.setBackground(new Color(255,192,230));
                botao.setForeground(Color.WHITE);
                
                // remover marcas do botao que vem por padrão
                botao.setFocusPainted(false);
                botao.setEnabled(false);
                
                final int linhaSelecionada= linha;
                final int colunaSelecionada= coluna;
                
                botao.addActionListener((ActionEvent Evento)->{
                        abrirBotao(linhaSelecionada,colunaSelecionada);
                        
                        });
                
                //adicionar o batao dentro da massa da matriz
                btnCampo[linha][coluna]=botao;
                // adcionar ele dentro do painel
                painelCampo.add(botao);
                
          
                
                
            }// fim do 2 for
            }// fim do 1 for
        }
     
     public void AdicionarBombas(){
        //Criar uma variavel Random para gerar valores aleatorios
        Random sorteador = new Random();
        int bombasAdicionadas = 0;
        
        while( bombasAdicionadas < quantidadeBombas){
            int linha = sorteador.nextInt(10);
            int coluna = sorteador.nextInt(10);
            if(!bombas[linha][coluna]){
                bombas[linha][coluna]=true;
                bombasAdicionadas++;
            }
        }
        
        
    }// fim do AdicionarBombas


   public void IniciarJogo(){
        //chamar o metodo adicionarBombas
        AdicionarBombas();
        IniciarCronometro();
        //depois precisamos iniciar os botoes do jogo
        for(int colunas=0;colunas<=9;colunas++){
            for(int linhas=0;linhas<=9;linhas++){
                JButton botao = btnCampo[linhas][colunas];
                //deixar os botoes visiveis e clicaveis
                botao.setEnabled(true);
            }//fim do 2° for
        }//fim do 1° for
        btniniciar.setText("REINICIAR");
    }//fim do iniciar jogo
    
    
    public void abrirBotao(int linha, int coluna){
        // verificar se o jogo foi finalizado
        if(jogoEncerrado) return;
        
        //verificar se o botao ja foi aberto
        if(abertos[linha][coluna]) return;
      
        /*se o jogo ainda estiver rodando e o botão ainda não tiver
        sido aberto - então vamos abrir o botão*/
        abertos[linha][coluna]=true;
        quantidadeCasasAbertas++;
        
        // acessar o que tem dentro do botão
        JButton botao = btnCampo[linha][coluna];
        //se no botão tiver uma bomba, então vamos mostrar a bomba a ele
        if(bombas[linha][coluna]){
            //variavel que recebe nossa imagem
           ImageIcon imgBomba = new ImageIcon( 
                   getClass().getResource("/assets/bomb.png"));
           //colocar a imagem no botao
           botao.setIcon(imgBomba);
                       FinalizarJogo(false);
           return;
        }else{
            ImageIcon imgBandeira = new ImageIcon(
            getClass().getResource("/assets/flag.png"));
            botao.setIcon(imgBandeira);
            return;
        }
        
    }// fim do metodo abrirBotao
     
    public void FinalizarJogo(boolean venceu){
        mostrarBombas();
        jogoEncerrado=true;
        
        if(venceu){
            
            JOptionPane.showMessageDialog(this,"You Win!");
            LimparJogo();
        }else{
            JOptionPane.showMessageDialog(this,"Oops, you lose!");
            
            
        }
    }
     
    public void VerificarVitoria(){
    
    
    
        
        
        
        int casasSemBomba= 100 - quantidadeBombas;
        
        
        if(quantidadeCasasAbertas == casasSemBomba){
            FinalizarJogo(true);
            
            
        }
                
                }
    
    public void IniciarCronometro(){
    
    if(cronometro !=null){
    cronometro.stop();
    }
       segundoPassados = 0;
       tftempo.setText("00:00");
       
       
       cronometro = new Timer(1000, Eventos->{
           segundosPassados++;
           int minutos = segundosPassados/60;
           int horas = minutos/60;
           int segundo = segundosPassados%60;
           tftempo.setText(
           String.format("%02d:%02d:%02d", horas,minutos,segundo));
           
       });
       cronometro.start();
    }
    
    public void LimparJogo(){
        for(int linha=0;linha<=9;linha++){
            int coluna = 0;
            bombas[linha][coluna]=false;
            abertos[linha][coluna]=false;
            quantidadeCasasAbertas=0;
            
            
            JButton botao = btnCampo[linha][coluna];
            botao.setIcon(null);
        }
    }
    
    public void mostrarBombas(){
        for(int coluna=0;coluna<=9;coluna++){
        for(int linha=0;linha<=9;linha++){
            
            JButton botao = btnCampo[linha][coluna];
        //se no botão tiver uma bomba, então vamos mostrar a bomba a ele
        if(bombas[linha][coluna]){
            //variavel que recebe nossa imagem
           ImageIcon imgBomba = new ImageIcon( 
                   getClass().getResource("/assets/bomb.png"));
           //colocar a imagem no botao
           botao.setIcon(imgBomba);
                      
         
                
              
                        
            
        }
        }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        painelCampo = new javax.swing.JPanel();
        btniniciar = new javax.swing.JButton();
        tftempo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setText("Campo Minado");

        painelCampo.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout painelCampoLayout = new javax.swing.GroupLayout(painelCampo);
        painelCampo.setLayout(painelCampoLayout);
        painelCampoLayout.setHorizontalGroup(
            painelCampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 471, Short.MAX_VALUE)
        );
        painelCampoLayout.setVerticalGroup(
            painelCampoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        btniniciar.setText("iniciar");
        btniniciar.addActionListener(this::btniniciarActionPerformed);

        tftempo.setText("00:00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tftempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btniniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addComponent(painelCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(253, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(tftempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btniniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(366, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(painelCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(513, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btniniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btniniciarActionPerformed
        // TODO add your handling code here:
        IniciarJogo();
    }//GEN-LAST:event_btniniciarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Jogo2().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btniniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel painelCampo;
    private javax.swing.JTextField tftempo;
    // End of variables declaration//GEN-END:variables

   
}
