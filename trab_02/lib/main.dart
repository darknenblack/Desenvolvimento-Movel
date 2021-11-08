import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:trab_02/provider/pacotes.dart';
import 'package:trab_02/routes/app_routes.dart';
import 'package:trab_02/views/pacote_card.dart';
import 'package:trab_02/views/pacote_form.dart';
import '../views/pacotes_list.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(
            create: (ctx) => Pacotes(),
        ),
      ],
      child: MaterialApp(
          theme: ThemeData(
            primarySwatch: Colors.blue,
            visualDensity: VisualDensity.adaptivePlatformDensity,
        ),
        routes: {
            AppRoutes.HOME: (_) => PacoteList(),
            AppRoutes.PACOTE_FORM: (_) => PacoteForm(),
            AppRoutes.PACOTE_CARD: (_) => PacoteCard(),
        },
        debugShowCheckedModeBanner: false,
      ),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: (){},
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
