import 'package:flutter/cupertino.dart';

class Pacote {
  final String id;
  final String name;
  final String descricao;
  final String local;
  final String data;
  final String hora;

  const Pacote({
    required this.id,
    required this.name,
    required this.descricao,
    required this.data,
    required this.hora,
    required this.local
  });
}