import 'package:flutter/material.dart';

class Pacote {
  final String id;
  final String name;
  final String descricao;
  final String local;
  final String data;
  final String hora;
  final String codigo;

  const Pacote({
    required this.id,
    required this.name,
    required this.descricao,
    required this.data,
    required this.hora,
    required this.local,
    required this.codigo,
  });

  factory Pacote.fromJson(Map<String, dynamic> json) {
    return Pacote(
      codigo: json['codObjeto'],
      id: '', hora: '', descricao: '', local: '', data: '', name: '',
    );
  }
}