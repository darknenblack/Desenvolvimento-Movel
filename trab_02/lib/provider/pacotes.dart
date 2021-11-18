import 'dart:math';
import 'package:flutter/cupertino.dart';
import 'package:trab_02/data/dummy_pacotes.dart';
import 'package:trab_02/models/pacote.dart';

class Pacotes with ChangeNotifier{
  final Map<String, Pacote> _items = {...dummy_pacotes};

  List<Pacote> get all{
    return [..._items.values];
  }

  int get count {
    return _items.length;
  }

  Pacote byIndex(int i){
    return _items.values.elementAt(i);
  }

  //inclui ou altera um pacote
  void put(Pacote pacote){

    if(pacote.codigo!.trim().isNotEmpty &&
        _items.containsKey(pacote.id)){
      _items.update(pacote.id!, (_) => pacote);
    }

    final id = Random().nextDouble().toString();

    _items.putIfAbsent(id, () => Pacote(
      name: pacote.name,
      codigo: pacote.codigo,
      id: pacote.id,
    ));
    notifyListeners();
  }

  void remove(Pacote pacote){
    _items.remove(pacote.id);
    notifyListeners();
  }
}