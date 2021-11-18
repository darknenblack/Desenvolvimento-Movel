
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:provider/provider.dart';
import 'package:trab_02/provider/pacotes.dart';
import 'package:trab_02/routes/app_routes.dart';
import '../components/pacote_tile.dart';

class PacoteList extends StatelessWidget {
  const PacoteList({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final Pacotes pacotes = Provider.of(context);

    return Scaffold(
      appBar: AppBar(
        title: Text('Lista de Pacotes'),
        /*actions: <Widget>[
          IconButton(onPressed: (){
            ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Atualizando..')));
          }, icon: const Icon(Icons.update))
        ],*/
      ),
      body: ListView.builder(
        itemCount: pacotes.count,
        itemBuilder: (ctx, i) => PacoteTile(pacotes.byIndex(i)),

      ),


      floatingActionButton: FloatingActionButton(
        onPressed: (){
          Navigator.of(context).pushNamed(
            AppRoutes.PACOTE_FORM
          );
        },
        tooltip: 'Adicionar pacote',
        child: Icon(Icons.add),
      ),
    );
  }
}
