import 'dart:convert';
import 'dart:developer';
import 'dart:async';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';


class PacoteCard extends StatefulWidget {
  const PacoteCard({Key? key}) : super(key: key);

  @override
  PacoteCard2 createState() => PacoteCard2();
}

class PacoteCard2 extends State<PacoteCard> {
  // init the step to 0th position
  int current_step = 0;
  int i = 0;
  //var jsonResponseDataDecode;

  List<Step> my_steps = [
    Step(
      // STEP 1
        title: Text("Step 1"),
        // RECEBER TEXTO DA LOCALIDADE
        content: Text("Hello!"),
        isActive: true),

    Step(
        title: Text("Step 2"),
        content: Text("World!"),
        // You can change the style of the step icon i.e number, editing, etc.
        //state: StepState.editing,
        isActive: true),
    Step(
        title: Text("Step 3"),
        content: Text("Hello World!"),
        isActive: true),
  ];


  //var jsonResponseDataDecode = getData("QH797101600BR");

/*  for(i = 0; i < jsonResponseDataDecode.eventos.lenght() ; i++){
    mysteps.add(Step(
      title: jsonResponseDataDecode['eventos'][0]['descricao'][i],
      content: jsonResponseDataDecode['eventos'][0]['dtHrCriado'][i] + jsonResponseDataDecode['eventos'][0]['cidade'][i] +
      jsonResponseDataDecode['eventos'][0]['uf'][i] + jsonResponseDataDecode['eventos'][0]['tipo'][i],
      isActive: true,
    ));
  }*/


  @override
  Widget build(BuildContext context) {

    //var jsonResponseDataDecode = getData("QH797101600BR");



    //print((await jsonResponseDataDecode)['objetos']);

/*  */

    return Scaffold(
      // Appbar
      appBar: AppBar(
        // Title
          // PEGAR NOME DA ENCOMENDA
        title: Text("ENCOMENDA TESTE"),
      ),
      body: buildFutureBuilder(),
    );
  }

  buildFutureBuilder() {
    return FutureBuilder<Post2>(
        future : getData("QH797101600BR"),
        builder: (context, snapshot) {
          switch(snapshot.connectionState){
            case ConnectionState.none :
            case ConnectionState.waiting:
              return Center(
                  child: Text("Carregando...")
              );
            default:
              if(snapshot.hasError){
                return Center(
                  child: Text("Erro ao carregar..."),
                );
              } else {


                return Scaffold(
                  // Appbar
                  appBar: AppBar(
                    // Title
                    // PEGAR NOME DA ENCOMENDA
                    title: Text(snapshot.data!.title.toString()),
                  ),
                  // Body
                  body: Container(
                      child: Stepper(
                        currentStep: this.current_step,
                        steps: my_steps,
                        type: StepperType.vertical,
                        onStepTapped: (step) {
                          // On hitting step itself, change the state and jump to that step
                          setState(() {
                            current_step = step;
                          });
                          // Log function call
                          print("onStepTapped : " + step.toString());
                        },
                      )),
                );
              }
          }
        });
  }

  Future<Post2> getData(String value) async {
    final response = await http.get(Uri.parse('https://proxyapp.correios.com.br/v1/sro-rastro/'+ value));
    log(json.decode(response.body).toString());
    //print(json.decode(response.body));
    var jdec = await json.decode(response.body);
    Post2 aux = Post2.fromJson(jdec);
    print(aux.title.toString());

    return aux;
  }

}

class Post2 {
  final String title;
  final String content;
  final bool isActive;

  Post2({this.title ="13", this.content="13" , this.isActive = true });

  factory Post2.fromJson(Map<String, dynamic> json) {
    return Post2(
      title: json['resultado'],
      content: json['resultado'],
      isActive: json['resultado'],
    );
  }
}