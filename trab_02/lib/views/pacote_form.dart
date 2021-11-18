import 'dart:convert';
import 'dart:developer';
import 'dart:async';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:provider/provider.dart';
import 'package:trab_02/models/pacote.dart';
import 'package:trab_02/provider/pacotes.dart';

class PacoteForm extends StatelessWidget {
  final _form = GlobalKey<FormState>();
  final Map<String, String> _formData = {};

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Formulario Pacote'),
        actions: <Widget>[
          IconButton(
            onPressed: (){
              final isValid = _form.currentState!.validate();

              if(isValid) {
                _form.currentState!.save();

                Provider.of<Pacotes>(context, listen:false).put(Pacote(
                  id: _formData['id'] ?? '0',
                  name: _formData['name'] ?? 'vazio',
                  descricao: _formData['descricao'] ?? 'vazio',
                  local: _formData['local'] ?? 'vazio',
                  data: _formData['data'] ?? 'vazio',
                  hora: _formData['hora'] ?? 'vazio',
                  codigo: _formData['codigo'] ?? 'vazio',
                ),
                );

                Navigator.of(context).pop();
              }
            },
            icon: Icon(Icons.save),
          ),
        ],
      ),
      body:Padding(
        padding: EdgeInsets.all(15),
        child: Form(
          key: _form,
          child: Column(
            children: <Widget>[
              TextFormField(
                decoration: InputDecoration(labelText: 'Nome: '),
                onSaved:(value) => _formData['name'] = value!,
                validator: (value) {
                  if (value == null || value.trim().isEmpty) {
                    return 'Campo brigatório';
                  }
                },
              ),
              TextFormField(
                decoration: InputDecoration(labelText: 'Código: '),
                onSaved: (value) => _formData['name'] = value!,
                validator: (value) {
                  if (value == null || value.trim().isEmpty) {
                    return 'Campo brigatório';
                  }
                },
              ),
            ],
          ),
        ),
      )
    );
  }

  Future<Map> getData(String value) async {
    http.Response response = await http.get(Uri.parse('https://proxyapp.correios.com.br/v1/sro-rastro/%27QH797101600BR'));

    log(json.decode(response.body).toString());
    //print(json.decode(response.body));
    return json.decode(response.body);
  }

}

