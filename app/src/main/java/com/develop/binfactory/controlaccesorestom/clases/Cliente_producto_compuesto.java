package com.develop.binfactory.controlaccesorestom.clases;
import java.util.ArrayList;
import com.develop.binfactory.controlaccesorestom.logica.soporte.Utils;
import com.develop.binfactory.controlaccesorestom.controladores.*;
import android.database.Cursor;
import android.content.Context;

public class Cliente_producto_compuesto
	{
		public int fID;
		public int fproducto_compuesto_ID;
		public int fcliente_proveedor_ID;
		public int fprecio;
		public String fnombre_producto;
		
	//CONSTRUCTOR
	public Cliente_producto_compuesto(Cursor cursor)
	{
		try
	{
	//cursor.getString(11)
	if (cursor.getColumnIndex("_id") > -1)
	fID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("_id")));
	else
	fID = Integer.valueOf(cursor.getString(cursor.getColumnIndex("ID")));
	
	fproducto_compuesto_ID =Integer.valueOf(cursor.getString(cursor.getColumnIndex("producto_compuesto_ID")));
	fcliente_proveedor_ID =Integer.valueOf(cursor.getString(cursor.getColumnIndex("cliente_proveedor_ID")));
	fprecio =Integer.valueOf(cursor.getString(cursor.getColumnIndex("precio")));
	fnombre_producto =cursor.getString(cursor.getColumnIndex("nombre_producto"));
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"Cliente_producto_compuesto.Constructor");
	}
	}
	public Cliente_producto_compuesto()
	{
	}
	
	public int getID(){
		return fID;
	}
	public int getProducto_compuesto_ID(){
		return fproducto_compuesto_ID;
	}
	public int getCliente_proveedor_ID(){
		return fcliente_proveedor_ID;
	}
	public int getPrecio(){
		return fprecio;
	}
	public String getNombre_producto(){
		if(fnombre_producto!=null)
		return fnombre_producto;
		else
		return "";
	}
	
	public void setID(int ID){
		this.fID=ID;
	}
	
	public void setProducto_compuesto_ID(int producto_compuesto_ID){
		this.fproducto_compuesto_ID=producto_compuesto_ID;
	}
	
	public void setCliente_proveedor_ID(int cliente_proveedor_ID){
		this.fcliente_proveedor_ID=cliente_proveedor_ID;
	}
	
	public void setPrecio(int precio){
		this.fprecio=precio;
	}
	
	public void setNombre_producto(String nombre_producto){
		this.fnombre_producto=nombre_producto;
	}
	
	
	public void actualizar(Context context)
	{
		try
	{
		CtrlCliente_producto_compuesto.actualizar(this,context);
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"Cliente_producto_compuesto.actualizar");
	}
	}
	public int ingresar(Context context)
	{
		return  CtrlCliente_producto_compuesto.ingresar(this,context);
	}
	public void eliminar(Context context)
	{
		try
	{
		CtrlCliente_producto_compuesto.eliminar(this.fID,context);
	}
	catch(Exception ex)
	{
		Utils.escribeLog(ex,"Cliente_producto_compuesto.eliminar");
	}
	}
	
	
	
	
	
	public Producto_compuesto getProducto_compuesto(Context context)
	{
		return CtrlProducto_compuesto.getProducto_compuesto(this.fproducto_compuesto_ID,context);
	}
	public Cliente_proveedor getCliente_proveedor(Context context)
	{
		return CtrlCliente_proveedor.getCliente_proveedor(this.fcliente_proveedor_ID,context);
	}
	
	}//fin clase lógica
	
	
	
