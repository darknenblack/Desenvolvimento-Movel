
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import '../components/pacote_tile.dart';
import '../data/dummy_pacotes.dart';

class PacoteList extends StatelessWidget {
  const PacoteList({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final pacotes = {...dummy_pacotes};

    return Scaffold(
      appBar: AppBar(
        title: Text('Lista de Pacotes'),
        actions: <Widget>[
          IconButton(onPressed: (){
            ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Atualizando..')));
          }, icon: const Icon(Icons.update))
        ],
      ),
      body: ListView.builder(
        itemCount: pacotes.length,
        itemBuilder: (ctx, i) => PacoteTile(pacotes.values.elementAt(i)),
      ),

      floatingActionButton: FloatingActionButton(
        onPressed: null,
        tooltip: 'Adicionar pacote',
        child: Icon(Icons.add),
      ),
    );
  }
}
