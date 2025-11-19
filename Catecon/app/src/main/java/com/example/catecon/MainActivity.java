package com.example.catecon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText diaIn, mesIn, anoIn, diaFn, mesFn,anoFn;
    private Button calcular, limpar;
    private TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        diaIn = findViewById(R.id.ETDiaInicial);
        mesIn = findViewById(R.id.ETMesInicial);
        anoIn = findViewById(R.id.ETAnoInicial);
        mesFn = findViewById(R.id.ETMesFinal);
        diaFn = findViewById(R.id.ETDiaFinal);
        anoFn = findViewById(R.id.ETAnoFinal);
        resultado = findViewById(R.id.txtResultado);

        calcular = findViewById(R.id.btnCalcular);
        limpar = findViewById(R.id.btnLimpar);

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculoDias();
            }
        });
    }
    public int diasAnosInteiros(){
        int anoInicial = Integer.parseInt(anoIn.getText().toString());
        int anoFinal = Integer.parseInt(anoFn.getText().toString());

        List<Integer> anosInteiros = new ArrayList<>();
        List<Integer> diasInteiros = new ArrayList<>();

        int qtdInt = anoFinal-anoInicial-1;
        int somaDias=0;
        int anos = anoInicial;

        //Adiciona os anos inteiros à lista
        for(int i=0;i!=qtdInt;i++){
            anos++;
            anosInteiros.add(anos);
        }

        //Adiciona os dias, conforme os anos bissextos
        for(Integer x: anosInteiros){
            if (x%4==0 && x%100!=0 || x%400==0){
                diasInteiros.add(366);
            }else{
                diasInteiros.add(365);
            }
        }

        //Soma pela iteratividade os dias adicionados à lista
        for (Integer y: diasInteiros){
            somaDias += y;
        }

        return somaDias;
    }


    //Método para calcular os dias trabalhados do ano inicial
    public int diasAnoInicial(){
        int diaInicial = Integer.parseInt(diaIn.getText().toString());
        int mesInicial = Integer.parseInt(mesIn.getText().toString());
        int anoInicial = Integer.parseInt(anoIn.getText().toString());

        List<Integer> diasMes = new ArrayList<>();

        //Índice referente ao mês de inicio
        int indice = mesInicial-1;
        //Dias do 1° mês
        int diasMes1 ;

        int somaDIn=0;

        //Adiciona à lista todos os dias do mÊs conforme ano, caso bissexto ou não
        if(anoInicial%4==0 && anoInicial%100!=0 || anoInicial%400==0){
            diasMes.add(31);diasMes.add(29);diasMes.add(31);diasMes.add(30);
            diasMes.add(31);diasMes.add(30);diasMes.add(31);diasMes.add(31);
            diasMes.add(30);diasMes.add(31);diasMes.add(30);diasMes.add(31);
        }else{
            diasMes.add(31);diasMes.add(28);diasMes.add(31);diasMes.add(30);
            diasMes.add(31);diasMes.add(30);diasMes.add(31);diasMes.add(31);
            diasMes.add(30);diasMes.add(31);diasMes.add(30);diasMes.add(31);
        }

        //Soma dos dias do 1° mês
        diasMes1 = diasMes.get(indice) - diaInicial+1;
        //Retirando os meses que não foram trabalhados
        for(int i=0;i!=mesInicial;i++){
            diasMes.remove(0);
        }
        //Somando os dias de trabalho dos meses inteiros  do 1° ano
        for (Integer y: diasMes){
            somaDIn += y;
        }

        somaDIn +=diasMes1;
        return somaDIn;
    }


    //Método para calcular os dias trabalhados do último ano
    public int diasAnoFinal(){
        int diaFinal = Integer.parseInt(diaFn.getText().toString());
        int mesFinal = Integer.parseInt(mesFn.getText().toString());
        int anoFinal = Integer.parseInt(anoFn.getText().toString());

        List<Integer> diasMesFin = new ArrayList<>();

        //Variável que guarda a quantidade de meses inteiros trabalhados
        int mesesInt = mesFinal-1;
        //Cálculo para descobrir quantos meses completos não foram trabalhados
        int conta = 12-mesesInt ;

        int somaDFinal=0;

        //Adiciona à lista todos os dias do mÊs conforme ano, caso bissexto ou não
        if(anoFinal%4==0 && anoFinal%100!=0 || anoFinal%400==0){
            diasMesFin.add(31);diasMesFin.add(29);diasMesFin.add(31);diasMesFin.add(30);
            diasMesFin.add(31);diasMesFin.add(30);diasMesFin.add(31);diasMesFin.add(31);
            diasMesFin.add(30);diasMesFin.add(31);diasMesFin.add(30);diasMesFin.add(31);
        }else{
            diasMesFin.add(31);diasMesFin.add(28);diasMesFin.add(31);diasMesFin.add(30);
            diasMesFin.add(31);diasMesFin.add(30);diasMesFin.add(31);diasMesFin.add(31);
            diasMesFin.add(30);diasMesFin.add(31);diasMesFin.add(30);diasMesFin.add(31);
        }

        Collections.reverse(diasMesFin);
        //Loop para remover os meses não trabalhados por completo
        for(int i=0;i!=conta;i++){
            diasMesFin.remove(0);
        }


        //Somando os dias de trabalho dos meses inteiros do ano final
        for (Integer x: diasMesFin){
            somaDFinal += x;
        }

        somaDFinal += diaFinal;
        return somaDFinal;
    }


    //Método para realizar o cálculo final da quantidade de dias trabalhados
    public void calculoDias(){
        //Alocando o resultado do método a uma variável
        int anosD = diasAnosInteiros();
        int inicioD = diasAnoInicial();
        int fimD = diasAnoFinal();

        double totalDias = anosD+inicioD+fimD;

        double anos = totalDias/365;
        int anoss = (int)anos;

        double anq = (anos-(int)anos)*365;

        double meses = anq/30;
        int mesesInt = (int)meses;

        double dias = (meses-mesesInt)*30;
        int diass = (int)dias;

        resultado.setText("Resultado: "+anoss+" anos, "+mesesInt+" meses e "+Math.round(dias)+" dias");



    }
}

