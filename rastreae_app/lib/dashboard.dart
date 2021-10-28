import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:rastreae_app/presentation/my_flutter_app_icons.dart';

class Dashboard extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
        title: const Text('RastreiAê'),
        ),
        body: Container(
        padding: EdgeInsets.fromLTRB(10,10,10,0),
        height: 220,
        width: double.maxFinite,
         child: Card(
           child: Padding(
             padding: EdgeInsets.all(7),
             child: Stack(
                 children: <Widget>[
                   Align(
                     alignment: Alignment.centerRight,
                     child: Stack(
                       children: <Widget>[
                         Padding(
                             padding: const EdgeInsets.only(left: 10, top: 5),
                             child: Column(
                               children: <Widget>[
                                 Row(
                                   children: <Widget>[
                                     Padding(
                                       padding: const EdgeInsets.only(left: 15.0),
                                       child: Align(
                                           alignment: Alignment.centerLeft,
                                           child: Icon(
                                             MyFlutterApp.truck_1,
                                             color: Colors.amber,
                                             size: 40,
                                           )),
                                     )
                                   ],
                                 )
                               ],
                             ))
                       ],
                     ),
                   )
                 ]),
           ),
         ),
        ),
      ),
    );
  }

}