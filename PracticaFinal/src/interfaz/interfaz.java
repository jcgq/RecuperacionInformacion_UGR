
package interfaz;
import busqueda.funcionesBusqueda;
import static busqueda.funcionesBusqueda.busquedaAplicandoFaceta;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
/**
 *
 * @author Juanca y Pedro
 */
public class interfaz extends javax.swing.JFrame {

    public DefaultListModel modelo;
    public static Query query;
    
    public interfaz() {
        initComponents();
        salida.setLineWrap(true);
        salida.setWrapStyleWord(true);
        salida.setEditable(false);
        salidaBooleana.setLineWrap(true);
        salidaBooleana.setWrapStyleWord(true);
        modelo=new DefaultListModel();
        query = null;
    }


    @SuppressWarnings("unchecked")
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        consulta = new javax.swing.JTextField();
        boton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        salida = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        salidaFacetas = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        listaConsulta = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        listaConsultaBooleana1 = new javax.swing.JComboBox<>();
        listaConsultaBooleana2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        consultaBooleana2 = new javax.swing.JTextField();
        consultaBooleana1 = new javax.swing.JTextField();
        condicionBooleano = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        salidaBooleana = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        listaFacetas = new javax.swing.JComboBox<>();
        aplicarConsulta = new javax.swing.JCheckBox();
        botonBooleano = new javax.swing.JButton();
        isCon1 = new javax.swing.JComboBox<>();
        isCon2 = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        salidaFrases = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        consultaFrases = new javax.swing.JTextField();
        botonFrases = new javax.swing.JButton();
        listaConsultaFrases = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        nDocs = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        facetar = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        listaFacetasSelec = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        boton.setText("Consultar");
        boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActionPerformed(evt);
            }
        });

        jLabel2.setText("Consulta");

        jLabel3.setText("Salida");

        salida.setColumns(20);
        salida.setRows(5);
        jScrollPane1.setViewportView(salida);

        salidaFacetas.setColumns(20);
        salidaFacetas.setRows(5);
        jScrollPane3.setViewportView(salidaFacetas);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 337, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 129, Short.MAX_VALUE)
        );

        listaConsulta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTORES", "TITULO", "TEXTO", "PAISES", "LONGITUD_APROX", "UNIVERSIDAD", "LUGAR" }));
        listaConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaConsultaActionPerformed(evt);
            }
        });

        jLabel1.setText("¿Por que campo quieres realizar la consulta?");

        jLabel4.setText("Puede seleccionar los campos por los que quiere visualizar las facetas");

        jLabel5.setText("Documentos de la consulta");

        jLabel6.setText("Facetas");

        jLabel7.setText("Consulta Booleana");

        listaConsultaBooleana1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTORES", "TITULO", "TEXTO", "PAISES", "UNIVERSIDAD", "LUGAR" }));
        listaConsultaBooleana1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaConsultaBooleana1ActionPerformed(evt);
            }
        });

        listaConsultaBooleana2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTORES", "TITULO", "TEXTO", "PAISES", "UNIVERSIDAD", "LUGAR" }));
        listaConsultaBooleana2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaConsultaBooleana2ActionPerformed(evt);
            }
        });

        jLabel8.setText("Consulta");

        condicionBooleano.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AND", "OR" }));
        condicionBooleano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                condicionBooleanoActionPerformed(evt);
            }
        });

        salidaBooleana.setColumns(20);
        salidaBooleana.setRows(5);
        jScrollPane2.setViewportView(salidaBooleana);

        jLabel9.setText("Salida");

        listaFacetas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODAS", "AUTORES", "PAISES", "UNIVERSIDAD", "LUGAR", "RANGOS", "LONGITUD" }));
        listaFacetas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaFacetasActionPerformed(evt);
            }
        });

        aplicarConsulta.setText("Aplicar Consulta");
        aplicarConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicarConsultaActionPerformed(evt);
            }
        });

        botonBooleano.setText("Consultar");
        botonBooleano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBooleanoActionPerformed(evt);
            }
        });

        isCon1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CONTENGA", "NO_CONTENGA" }));
        isCon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isCon1ActionPerformed(evt);
            }
        });

        isCon2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CONTENGA", "NO_CONTENGA" }));
        isCon2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isCon2ActionPerformed(evt);
            }
        });

        salidaFrases.setColumns(20);
        salidaFrases.setRows(5);
        jScrollPane4.setViewportView(salidaFrases);

        jLabel10.setText("Salida");

        botonFrases.setText("Consultar");
        botonFrases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonFrasesActionPerformed(evt);
            }
        });

        listaConsultaFrases.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AUTORES", "TITULO", "TEXTO", "UNIVERSIDAD" }));
        listaConsultaFrases.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaConsultaFrasesActionPerformed(evt);
            }
        });

        jLabel12.setText("¿Por que campo quieres realizar la consulta por frases?");

        jLabel11.setText("NÚMERO DE DOCUMENTOS");

        facetar.setText("Facetar");
        facetar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facetarActionPerformed(evt);
            }
        });

        jScrollPane5.setViewportView(listaFacetasSelec);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(53, 53, 53)
                                        .addComponent(jLabel9)
                                        .addGap(29, 29, 29)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(420, 420, 420)
                                        .addComponent(botonBooleano)))
                                .addGap(98, 98, 98)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(18, 18, 18)
                                        .addComponent(isCon1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(consultaBooleana1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(condicionBooleano, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(isCon2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(listaConsultaBooleana1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(249, 249, 249)
                                                .addComponent(jLabel7)))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(consultaBooleana2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(listaConsultaBooleana2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(189, 189, 189)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(listaConsultaFrases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(consultaFrases, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(botonFrases)
                                        .addGap(244, 244, 244))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(57, 57, 57)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(260, 260, 260)
                                                .addComponent(listaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(379, 379, 379)
                                                .addComponent(nDocs, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(147, 147, 147)
                                                .addComponent(jLabel5))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(115, 115, 115)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel1)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel2)
                                                        .addGap(30, 30, 30)
                                                        .addComponent(consulta, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(270, 270, 270)
                                                        .addComponent(jLabel11))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addComponent(boton)))))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(78, 78, 78)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel6)
                                                        .addGap(97, 97, 97)
                                                        .addComponent(aplicarConsulta))
                                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(158, 158, 158)
                                                .addComponent(listaFacetas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(186, 186, 186)
                                                .addComponent(facetar)
                                                .addGap(134, 134, 134))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(15, 15, 15)))))))
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel12))
                .addGap(384, 384, 384))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listaConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(nDocs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(201, 201, 201)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(consulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(boton)
                                        .addComponent(jLabel2))
                                    .addGap(33, 33, 33)
                                    .addComponent(jLabel5))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(aplicarConsulta)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(listaFacetas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(facetar)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(listaConsultaFrases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(consultaFrases, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonFrases)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(61, 61, 61))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(listaConsultaBooleana2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(listaConsultaBooleana1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(isCon1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(isCon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(condicionBooleano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(consultaBooleana2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(consultaBooleana1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(163, 163, 163))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel9)
                        .addGap(240, 240, 240)))
                .addComponent(botonBooleano)
                .addGap(226, 226, 226)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public static void setQuery(Query _query){
        query = _query;
    }
    private void botonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActionPerformed
        salida.setText(null);
        salidaFacetas.setText(null);
        modelo=new DefaultListModel();
        Analyzer analyzer = new SimpleAnalyzer();
        Similarity similarity = new ClassicSimilarity();
        if(consulta!=null){
            if(!listaConsulta.getSelectedItem().toString().toLowerCase().equals("longitud"))
                salida.setText(funcionesBusqueda.buscadorPorTermino(query, analyzer, similarity, listaConsulta.getSelectedItem().toString().toLowerCase(), consulta.getText()));
            else{
                salida.setText(funcionesBusqueda.buscadorPorEntero(analyzer, similarity, "longitud", Integer.parseInt(consulta.getText())));
            }
            
            
                
        }
        //TODOSlosCampos AplicarConsultaConsulta
        if(aplicarConsulta.isSelected()){//Quiero buscar facetas con la consulta
            if(listaFacetas.getSelectedItem().toString().toLowerCase().equals("todas")){
                try {
                    String aux = "";
                    aux=funcionesBusqueda.busquedaFacetas(modelo, similarity, listaConsulta.getSelectedItem().toString().toLowerCase(), consulta.getText(), true, true, listaFacetas.getSelectedItem().toString().toLowerCase());
                    salidaFacetas.setText(aux);
                    listaFacetasSelec.setModel(modelo);
                } catch (ParseException ex) {
                    Logger.getLogger(interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                try {
                    String aux = "";
                    aux=funcionesBusqueda.busquedaFacetas(modelo, similarity, listaConsulta.getSelectedItem().toString().toLowerCase(), consulta.getText(), false, true, listaFacetas.getSelectedItem().toString().toLowerCase());
                    salidaFacetas.setText(aux);
                    listaFacetasSelec.setModel(modelo);
                } catch (ParseException ex) {
                    Logger.getLogger(interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                
            
        }
        else{
            if(listaFacetas.getSelectedItem().toString().toLowerCase().equals("todas")){
                
                try {
                    String aux = "";
                    aux=funcionesBusqueda.busquedaFacetas(modelo, similarity, listaConsulta.getSelectedItem().toString().toLowerCase(), consulta.getText(), true, false, listaFacetas.getSelectedItem().toString().toLowerCase());
                    salidaFacetas.setText(aux);
                    
                    listaFacetasSelec.setModel(modelo);
                } catch (ParseException ex) {
                    Logger.getLogger(interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                try {
                    String aux = "";
                    aux=funcionesBusqueda.busquedaFacetas(modelo, similarity, listaConsulta.getSelectedItem().toString().toLowerCase(), consulta.getText(), false, false, listaFacetas.getSelectedItem().toString().toLowerCase());
                    salidaFacetas.setText(aux);
                    
                    listaFacetasSelec.setModel(modelo);
                } catch (ParseException ex) {
                Logger.getLogger(interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        System.out.println("La consulta es " + query);
        nDocs.setText( Integer.toString(funcionesBusqueda.getNumDoc()));
        
        
        
    }//GEN-LAST:event_botonActionPerformed

    private void listaConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaConsultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaConsultaActionPerformed

    private void listaFacetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaFacetasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaFacetasActionPerformed

    private void aplicarConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aplicarConsultaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aplicarConsultaActionPerformed

    private void listaConsultaBooleana1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaConsultaBooleana1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaConsultaBooleana1ActionPerformed

    private void listaConsultaBooleana2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaConsultaBooleana2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaConsultaBooleana2ActionPerformed

    private void condicionBooleanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_condicionBooleanoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_condicionBooleanoActionPerformed

    private void botonBooleanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBooleanoActionPerformed
        // TODO add your handling code here:
        //Para las consultas BOOLEANAS
        Analyzer analyzer = new SimpleAnalyzer();
        Similarity similarity = new ClassicSimilarity();        
        salidaBooleana.setText(funcionesBusqueda.buscadorBooleano(isCon1.getSelectedItem().toString().toLowerCase(), isCon2.getSelectedItem().toString().toLowerCase(), condicionBooleano.getSelectedItem().toString().toLowerCase(), analyzer, similarity, listaConsultaBooleana1.getSelectedItem().toString().toLowerCase(), listaConsultaBooleana2.getSelectedItem().toString().toLowerCase(), consultaBooleana1.getText(), consultaBooleana2.getText()));
        nDocs.setText( Integer.toString(funcionesBusqueda.getNumDoc()));
    }//GEN-LAST:event_botonBooleanoActionPerformed

    private void isCon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isCon1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_isCon1ActionPerformed

    private void isCon2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isCon2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_isCon2ActionPerformed

    private void botonFrasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFrasesActionPerformed
        // TODO add your handling code here:
        //BOTON PARA LAS FRASES
        
        Analyzer analyzer = new SimpleAnalyzer();
        Similarity similarity = new ClassicSimilarity();
        salidaFrases.setText(funcionesBusqueda.buscadorFrases(analyzer, similarity, listaConsultaFrases.getSelectedItem().toString().toLowerCase(), consultaFrases.getText()));
        nDocs.setText( Integer.toString(funcionesBusqueda.getNumDoc()));
    }//GEN-LAST:event_botonFrasesActionPerformed

    private void listaConsultaFrasesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaConsultaFrasesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_listaConsultaFrasesActionPerformed

    private void facetarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facetarActionPerformed
        // TODO add your handling code here:
        //listaFacetasSelec.setModel(null);
        List<String> lista = listaFacetasSelec.getSelectedValuesList();
        DefaultListModel modeAux = new DefaultListModel();
        listaFacetasSelec.setModel(modeAux);
        listaFacetasSelec.setModel(modelo);
        Similarity similarity = new ClassicSimilarity();
        String aux = "";
        try {
            aux = busquedaAplicandoFaceta(similarity, query, lista.get(0), lista.get(1));
        } catch (IOException ex) {
            Logger.getLogger(interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        salida.setText(aux);
        try {
            modelo.clear();
            aux=funcionesBusqueda.busquedaFacetas(modelo, similarity, listaConsulta.getSelectedItem().toString().toLowerCase(), consulta.getText(), true, true, listaFacetas.getSelectedItem().toString().toLowerCase());
        } catch (ParseException ex) {
            Logger.getLogger(interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        salidaFacetas.setText(aux);
        
        
    }//GEN-LAST:event_facetarActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox aplicarConsulta;
    private javax.swing.JButton boton;
    private javax.swing.JButton botonBooleano;
    private javax.swing.JButton botonFrases;
    private javax.swing.JComboBox<String> condicionBooleano;
    private javax.swing.JTextField consulta;
    private javax.swing.JTextField consultaBooleana1;
    private javax.swing.JTextField consultaBooleana2;
    private javax.swing.JTextField consultaFrases;
    private javax.swing.JButton facetar;
    private javax.swing.JComboBox<String> isCon1;
    private javax.swing.JComboBox<String> isCon2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JComboBox<String> listaConsulta;
    private javax.swing.JComboBox<String> listaConsultaBooleana1;
    private javax.swing.JComboBox<String> listaConsultaBooleana2;
    private javax.swing.JComboBox<String> listaConsultaFrases;
    private javax.swing.JComboBox<String> listaFacetas;
    private javax.swing.JList<String> listaFacetasSelec;
    private javax.swing.JTextField nDocs;
    private javax.swing.JTextArea salida;
    private javax.swing.JTextArea salidaBooleana;
    private javax.swing.JTextArea salidaFacetas;
    private javax.swing.JTextArea salidaFrases;
    // End of variables declaration//GEN-END:variables
}
