import 'package:flutter/material.dart';
import 'package:trab_02/models/pacote.dart';
import '../models/my_icons_icons.dart';

class PacoteTile extends StatelessWidget {
  final Pacote pacote;
  const PacoteTile(this.pacote);

  @override
  Widget build(BuildContext context) {
    final icone = Icon(MyIcons.truck01, color: Colors.lightBlue,);
    return Card(
      elevation: 3,
      margin: EdgeInsets.all(10),

      child: ListTile(
        leading: icone,
        title: Text(pacote.name),
        subtitle: Text(pacote.descricao),

        trailing: IconButton(onPressed: () {}, icon: Icon(Icons.delete)),
      ),
    );
  }
}
