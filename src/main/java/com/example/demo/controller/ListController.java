package com.example.demo.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.data.Item;
import com.example.demo.data.ItemRepository;

@Controller
public class ListController {


  @Autowired
  ItemRepository itemRepository;

  //DB接続をするクラス
  @Autowired
  JdbcTemplate jdbcTemplate;

  //データベースから商品一覧を取得
  private List<Item> getItemList(){
    List<Item> list = jdbcTemplate.query("SELECT * FROM items ORDER BY item_id",
        new BeanPropertyRowMapper<Item>(Item.class));

//      List<Item> list = itemRepository.findAll();

        return list;
  }

  //データベースからカート一覧を取得
  private List<Item> getCartList(){
    List<Item> cartlists = jdbcTemplate.query("SELECT * FROM cartitems ORDER BY item_id",new BeanPropertyRowMapper<Item>(Item.class));

        return cartlists;
  }

  //商品の一覧とカートの一覧を表示
  @RequestMapping(value="/ListView", method = RequestMethod.GET)
  public String test(Model model,Model model2) {
    model.addAttribute("items", getItemList());
    model2.addAttribute("cartItems", getCartList());
    return "ListView";
  }

  // ショッピングカートに商品を追加
  @RequestMapping(value = "/addcartitem", method = RequestMethod.POST)
  public String insert(@Valid Item form, BindingResult result, Model model) {

    // 画面入力値にエラーがない場合
    if (!result.hasErrors()) {

      List<Item> cartlist =
          jdbcTemplate.query("SELECT * FROM items WHERE item_id = " + form.getItemId(),
              new BeanPropertyRowMapper<Item>(Item.class));

//      if (cartlist == null || cartlist.isEmpty()) {
//        return "ERROR";
//      }

      //リスト型からItem型に変換
      Item cart = cartlist.get(0);

      // 1行分の値をデータベースにINSERTする
      // SQL文字列中の「?」の部分に、後ろで指定した変数が埋め込まれる
      int insertNumber = jdbcTemplate.update("INSERT INTO cartitems VALUES( ?, ?, ? ,? )",
          Integer.parseInt(form.getItemId()),
          cart.getItemName(),
          Integer.parseInt(cart.getPrice()),
          Integer.parseInt(form.getNumber()));

    }

    return "redirect:/ListView";
  }

  @RequestMapping(value = "/deletecartitem", method = RequestMethod.GET) // URLとのマッピング
  public String update(@RequestParam(name = "item_id", required = true) String itemId,
      Model model) {

    // パラメータで受けとったアイテムIDのデータを削除する
    // SQL文字列中の「?」の部分に、後ろで指定した変数が埋め込まれる
    int deleteCount = jdbcTemplate.update("DELETE FROM cartitems WHERE item_id = ?", Integer.parseInt(itemId));

    return "redirect:/ListView";

  }

}
