import 'package:flutter/material.dart';
import 'package:trab_02/models/pacote.dart';
import 'package:trab_02/routes/app_routes.dart';
import '../models/my_icons_icons.dart';


class PacoteTile extends StatelessWidget {
  final Pacote pacote;
  PacoteTile(this.pacote);

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
        onTap: (){
          Navigator.of(context).pushNamed(
              AppRoutes.PACOTE_CARD,
              arguments: pacote,
          );
        },

        trailing: IconButton(
            onPressed: () {
              //pacotes.remove(pacotes.byIndex(1));
            },
            icon: Icon(Icons.delete),
            color:Colors.lightBlue),
      ),
    );
  }
}
