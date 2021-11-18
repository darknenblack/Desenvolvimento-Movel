import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:trab_02/models/pacote.dart';
import 'package:trab_02/provider/pacotes.dart';
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
        title: Text(pacote.name!),
        subtitle: Text(pacote.descricao!),
        onTap: (){
          Navigator.of(context).pushNamed(
              AppRoutes.PACOTE_CARD,
              arguments: pacote,
          );
        },

        trailing: IconButton(
            onPressed: () {
              showDialog(
                  context: context,
                  builder: (ctx) => AlertDialog(
                    title: Text('Excluir pacote'),
                    actions:<Widget> [
                      FlatButton(
                        onPressed: (){
                          Provider.of<Pacotes>(context, listen: false).remove(pacote);
                          Navigator.of(context).pop();
                        },
                        child: Text('Sim'),
                      ),
                      FlatButton(
                        onPressed: (){
                          Navigator.of(context).pop();
                        },
                        child: Text('Não'),
                      ),
                    ],
                  )
              );
            },
            icon: Icon(Icons.delete),
            color:Colors.lightBlue),
      ),
    );
  }
}
