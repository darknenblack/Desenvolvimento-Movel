import 'package:flutter/material.dart';
import 'package:help/pacote_card.dart';
import 'package:help/routes/app_routes.dart';

import 'my_icons.dart';
import 'sql_helper.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      // Remove the debug banner
        debugShowCheckedModeBanner: false,
        title: 'RastreiAe',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: const HomePage(),
        routes: {
          AppRoutes.PACOTE_CARD: (_) => PacoteCard(),
        },
    );
  }
}

bool _validate = false;

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  List<Map<String, dynamic>> _pacotes = [];

  bool _isLoading = true;
  void _refreshPacotes() async {
    final data = await SQLHelper.getItems();
    setState(() {
      _pacotes = data;
      _isLoading = false;
    });
  }

  @override
  void initState() {
    super.initState();
    _refreshPacotes(); // Loading the diary when the app starts
  }

  final TextEditingController _nomeController = TextEditingController();
  final TextEditingController _codigoController = TextEditingController();

  // This function will be triggered when the floating button is pressed
  // It will also be triggered when you want to update an item


  void _showForm(int? id) async {
    if (id != null) {
      final Pacote =
      _pacotes.firstWhere((element) => element['id'] == id);
      _nomeController.text = Pacote['nome'];
      _codigoController.text = Pacote['codigo'];
    }

    showModalBottomSheet(
        context: context,
        elevation: 5,
        builder: (_) => Container(
          padding: const EdgeInsets.all(15),
          width: double.infinity,
          height: 300,
          child: SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.end,
              children: <Widget>[
                TextFormField(
                  controller: _nomeController,
                  decoration: InputDecoration(hintText: 'Nome'),
                ),
                const SizedBox(
                  height: 10,
                ),
                TextFormField(
                  controller: _codigoController,
                  decoration: InputDecoration(hintText: 'Codigo'),
                ),
                const SizedBox(
                  height: 20,
                ),
                ElevatedButton(
                  onPressed: () async {
                    setState(() {
                      _codigoController.text.isEmpty ? _validate = true : _validate = false;
                    });
                    if (id == null) {
                      await _addItem();
                    }
                    if (id != null) {
                      await _updateItem(id);
                    }

                    _nomeController.text = '';
                    _codigoController.text = '';

                    Navigator.of(context).pop();
                  },
                  child: Text(id == null ? 'Adicionar Pacote' : 'Atualizar'),
                )
              ],
            ),
          ),
        ));
  }

// Insert a new journal to the database
  Future<void> _addItem() async {
    await SQLHelper.createItem(
        _nomeController.text, _codigoController.text);
    _refreshPacotes();
  }

  // Update an existing journal
  Future<void> _updateItem(int id) async {
    await SQLHelper.updateItem(
        id, _nomeController.text, _codigoController.text);
    _refreshPacotes();
  }

  // Delete an item
  void _deleteItem(int id) async {
    await SQLHelper.deleteItem(id);
    ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
      content: Text('Pacote deletado com sucesso!'),
    ));
    _refreshPacotes();
  }

  @override
  Widget build(BuildContext context) {
    final icone = Icon(MyIcons.truck01, color: Colors.lightBlue,);

    return Scaffold(
      appBar: AppBar(
        title: const Text('RastreiAê'),
      ),
      body: _isLoading
          ? const Center(
        child: CircularProgressIndicator(),
      )
          : ListView.builder(
        itemCount: _pacotes.length,
        itemBuilder: (context, index) => Card(
          margin: const EdgeInsets.all(10),
          elevation: 3,
          child: ListTile(
            onTap: (){
              Navigator.of(context).pushNamed(
                  AppRoutes.PACOTE_CARD,
                  arguments: _pacotes,
              );
            },
              leading: icone,
              title: Text(_pacotes[index]['nome']),
              subtitle: Text("Código: "+ _pacotes[index]['codigo']),
              trailing: SizedBox(
                width: 100,
                child: Row(
                  children: [
                    IconButton(
                      icon: const Icon(Icons.edit),
                      color:Colors.lightBlue,
                      onPressed: () => _showForm(_pacotes[index]['id']),
                    ),
                    IconButton(
                      icon: const Icon(Icons.delete),
                      color:Colors.lightBlue,
                      onPressed: () {
                          showDialog(
                              context: context,
                              builder: (ctx) => AlertDialog(
                                title: Text('Excluir pacote?'),
                                actions:<Widget> [
                                  FlatButton(
                                    onPressed: (){
                                      _deleteItem(_pacotes[index]['id']);
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
                    ),
                  ],
                ),
              )),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.add),
        onPressed: () => _showForm(null),
      ),
    );
  }
}