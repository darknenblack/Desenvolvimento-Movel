import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:trab_02/models/pacote.dart';

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
                onSaved: (value) => _formData['name'] = value!,
                validator: (value){
                  if(value == null || value.trim().isEmpty){
                    return 'Campo brigatório';
                  }
                },
              ),
              TextFormField(
                decoration: InputDecoration(labelText: 'Código: '),
                onSaved: (value){
                  fetchAlbum(value!);
                },
                validator: (value){
                  if(value == null || value.trim().isEmpty){
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

  Future<Pacote> fetchAlbum(String value) async {
    final response = await http
        .get(Uri.parse('https://proxyapp.correios.com.br/v1/sro-rastro/'+value));

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      return Pacote.fromJson(jsonDecode(response.body));
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load album');
    }
  }
}
