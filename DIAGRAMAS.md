# Diagramas del Sistema de Taller Mecánico (Hotwheels Tam)
## Formato XML compatible con Draw.io (diagrams.net)

Para visualizarlos en Draw.io:
1. Abre [draw.io](https://app.diagrams.net).
2. Ve a **Menú > Extras > Insertar > Avanzado > XML**.
3. Pega el bloque de código de cada diagrama y da clic en **Insertar**.

---

## 1. Diagrama de Casos de Uso Completo

Representa a los actores internos en el lado izquierdo y al actor externo **Cliente** en el lado derecho de la frontera del sistema. Incluye el flujo de sincronización de estatus de órdenes y vehículos, e indica que el arranque (Main) inicializa el esquema y los datos por defecto.

```xml
<mxfile host="app.diagrams.net">
  <diagram name="Casos de Uso - Hotwheels Tam">
    <mxGraphModel dx="1200" dy="800" grid="1" gridSize="10" guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="1" pageScale="1" pageWidth="1500" pageHeight="1100" math="0" shadow="0">
      <root>
        <mxCell id="0" />
        <mxCell id="1" parent="0" />

        <!-- Contenedor del Sistema -->
        <mxCell id="sys" value="Sistema de Gestión Hotwheels Tam" style="text;html=1;strokeColor=#3d3f43;fillColor=#f8f9fa;align=center;verticalAlign=top;spacingTop=8;fontSize=15;fontStyle=1;whiteSpace=wrap;rounded=1;" vertex="1" parent="1">
          <mxGeometry x="300" y="30" width="820" height="1020" as="geometry" />
        </mxCell>

        <!-- Nota de arranque Main -->
        <mxCell id="nota_main" value="&lt;b&gt;«arranque»&lt;/b&gt;&lt;br&gt;Main inicializa BD,&lt;br&gt;siembra datos y lanza LoginFrame" style="shape=note;whiteSpace=wrap;html=1;backgroundOutline=1;size=15;fillColor=#f5f5f5;strokeColor=#666666;fontColor=#333333;fontSize=10;" vertex="1" parent="1">
          <mxGeometry x="830" y="60" width="240" height="70" as="geometry" />
        </mxCell>

        <!-- ACTORES IZQUIERDA -->
        <mxCell id="act_superadmin" value="SuperAdmin" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="70" y="80" width="50" height="90" as="geometry" />
        </mxCell>

        <mxCell id="act_gerente" value="Gerente" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="70" y="280" width="50" height="90" as="geometry" />
        </mxCell>

        <mxCell id="act_empleado" value="Empleado / Recepción" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="70" y="490" width="50" height="90" as="geometry" />
        </mxCell>

        <mxCell id="act_mecanico" value="Mecánico" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1">
          <mxGeometry x="70" y="700" width="50" height="90" as="geometry" />
        </mxCell>

        <!-- ACTOR DERECHA -->
        <mxCell id="act_cliente" value="Cliente (Externo)" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1">
          <mxGeometry x="1220" y="490" width="50" height="90" as="geometry" />
        </mxCell>

        <!-- CASOS DE USO -->
        <mxCell id="uc_login" value="Iniciar Sesión / Autenticar" style="ellipse;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;fontStyle=1;" vertex="1" parent="1">
          <mxGeometry x="340" y="75" width="210" height="55" as="geometry" />
        </mxCell>

        <mxCell id="uc_usuarios_all" value="Gestionar Todos los Usuarios&#xa;(Crear SuperAdmin, Gerente, etc.)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="610" y="70" width="230" height="60" as="geometry" />
        </mxCell>

        <mxCell id="uc_usuarios_limited" value="Gestionar Usuarios Limitados&#xa;(Gerente: No SA/Ger | Empleado: Solo Cliente)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="610" y="155" width="260" height="60" as="geometry" />
        </mxCell>

        <mxCell id="uc_bitacora" value="Consultar Bitácora / Auditoría" style="ellipse;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="340" y="155" width="220" height="55" as="geometry" />
        </mxCell>

        <mxCell id="uc_reg_bitacora" value="Registrar Eventos en Bitácora" style="ellipse;whiteSpace=wrap;html=1;fillColor=#f5f5f5;strokeColor=#666666;fontStyle=2;" vertex="1" parent="1">
          <mxGeometry x="860" y="490" width="200" height="55" as="geometry" />
        </mxCell>

        <mxCell id="uc_personas" value="Gestionar Clientes y Mecánicos&#xa;(CRUD Personas)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="340" y="255" width="230" height="60" as="geometry" />
        </mxCell>

        <mxCell id="uc_vehiculos" value="Recepción de Vehículos&#xa;y Foto de Evidencia" style="ellipse;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="340" y="350" width="220" height="60" as="geometry" />
        </mxCell>

        <mxCell id="uc_inv_visual" value="Registrar Inventario Visual&#xa;(Daños en carrocería)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="620" y="350" width="220" height="60" as="geometry" />
        </mxCell>

        <mxCell id="uc_crear_orden" value="Crear Orden de Reparación" style="ellipse;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="340" y="450" width="220" height="60" as="geometry" />
        </mxCell>

        <mxCell id="uc_ordenes_mecanico" value="Consultar Mis Órdenes Asignadas" style="ellipse;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1">
          <mxGeometry x="340" y="545" width="230" height="60" as="geometry" />
        </mxCell>

        <mxCell id="uc_estatus_orden" value="Actualizar Estatus de Orden y Vehículo&#xa;(EN_REVISION, ESPERA_PIEZAS, LISTO)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1">
          <mxGeometry x="610" y="545" width="250" height="60" as="geometry" />
        </mxCell>

        <mxCell id="uc_presupuesto" value="Agregar Refacción / Mano de Obra&#xa;al Presupuesto (Descuenta Stock)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1">
          <mxGeometry x="610" y="640" width="250" height="60" as="geometry" />
        </mxCell>

        <mxCell id="uc_eliminar_orden" value="Eliminar Orden o Ítem de Presupuesto&#xa;(Solo SuperAdmin / Gerente)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="610" y="735" width="250" height="60" as="geometry" />
        </mxCell>

        <mxCell id="uc_refacciones" value="Gestionar Inventario de Refacciones&#xa;(Ver, Agregar Stock, Editar Precios)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="340" y="735" width="240" height="60" as="geometry" />
        </mxCell>

        <mxCell id="uc_refaccion_dao" value="Gestionar RefaccionDAO&#xa;(Foto, Stock, Precios)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="340" y="830" width="240" height="55" as="geometry" />
        </mxCell>

        <mxCell id="uc_cliente_vista" value="Consultar Estatus de Mi Vehículo&#xa;(Solo Lectura y Comentarios)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;fontStyle=1;" vertex="1" parent="1">
          <mxGeometry x="820" y="490" width="230" height="60" as="geometry" />
        </mxCell>

        <!-- CONEXIONES SuperAdmin -->
        <mxCell id="e_sa1" edge="1" parent="1" source="act_superadmin" target="uc_login"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_sa2" edge="1" parent="1" source="act_superadmin" target="uc_usuarios_all"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_sa3" edge="1" parent="1" source="act_superadmin" target="uc_bitacora"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_sa4" edge="1" parent="1" source="act_superadmin" target="uc_eliminar_orden"><mxGeometry relative="1" as="geometry" /></mxCell>

        <!-- CONEXIONES Gerente -->
        <mxCell id="e_g1" edge="1" parent="1" source="act_gerente" target="uc_login"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_g2" edge="1" parent="1" source="act_gerente" target="uc_usuarios_limited"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_g3" edge="1" parent="1" source="act_gerente" target="uc_bitacora"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_g4" edge="1" parent="1" source="act_gerente" target="uc_personas"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_g5" edge="1" parent="1" source="act_gerente" target="uc_eliminar_orden"><mxGeometry relative="1" as="geometry" /></mxCell>

        <!-- CONEXIONES Empleado -->
        <mxCell id="e_emp1" edge="1" parent="1" source="act_empleado" target="uc_login"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_emp2" edge="1" parent="1" source="act_empleado" target="uc_vehiculos"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_emp3" edge="1" parent="1" source="act_empleado" target="uc_crear_orden"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_emp4" edge="1" parent="1" source="act_empleado" target="uc_refacciones"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_emp5" edge="1" parent="1" source="act_empleado" target="uc_personas"><mxGeometry relative="1" as="geometry" /></mxCell>

        <!-- CONEXIONES Mecánico -->
        <mxCell id="e_mec1" edge="1" parent="1" source="act_mecanico" target="uc_login"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_mec2" edge="1" parent="1" source="act_mecanico" target="uc_ordenes_mecanico"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_mec3" edge="1" parent="1" source="act_mecanico" target="uc_estatus_orden"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_mec4" edge="1" parent="1" source="act_mecanico" target="uc_presupuesto"><mxGeometry relative="1" as="geometry" /></mxCell>

        <!-- CONEXIONES Cliente -->
        <mxCell id="e_cli1" edge="1" parent="1" source="act_cliente" target="uc_login"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_cli2" edge="1" parent="1" source="act_cliente" target="uc_cliente_vista"><mxGeometry relative="1" as="geometry" /></mxCell>

        <!-- INCLUDES -->
        <mxCell id="inc_inv" value="«include»" style="endArrow=open;endSize=12;dashed=1;html=1;" edge="1" parent="1" source="uc_vehiculos" target="uc_inv_visual"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="inc_b1" value="«include»" style="endArrow=open;endSize=12;dashed=1;html=1;" edge="1" parent="1" source="uc_crear_orden" target="uc_reg_bitacora"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="inc_b2" value="«include»" style="endArrow=open;endSize=12;dashed=1;html=1;" edge="1" parent="1" source="uc_estatus_orden" target="uc_reg_bitacora"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="inc_ref" value="«include»" style="endArrow=open;endSize=12;dashed=1;html=1;" edge="1" parent="1" source="uc_refacciones" target="uc_refaccion_dao"><mxGeometry relative="1" as="geometry" /></mxCell>

      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
```

---

## 2. Diagrama de Clases Completo (UML Oficial)

Este diagrama sigue de manera estricta la sintaxis formal de UML: herencias con triángulo blanco vacío apuntando hacia la clase padre, interfaces implementadas mediante línea discontinua con triángulo vacío, asociaciones directas y composiciones formales.

```xml
<mxfile host="app.diagrams.net">
  <diagram name="Diagrama de Clases UML — Hotwheels Tam">
    <mxGraphModel dx="1422" dy="900" grid="1" gridSize="10" guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="1" pageScale="1" pageWidth="1920" pageHeight="1700" math="0" shadow="0">
      <root>
        <mxCell id="0"/><mxCell id="1" parent="0"/>

        <!-- FONDOS DE PAQUETES (visuales, parent=1, NO contenedores) -->
        <mxCell id="bg_main" value="«package» com.taller" style="text;html=1;strokeColor=#b85450;fillColor=#fdecea;align=left;verticalAlign=top;spacingLeft=8;spacingTop=6;fontSize=11;fontStyle=1;" vertex="1" parent="1"><mxGeometry x="40" y="30" width="390" height="160" as="geometry"/></mxCell>
        <mxCell id="bg_modelo" value="«package» com.taller.modelo" style="text;html=1;strokeColor=#6c8ebf;fillColor=#f0f4ff;align=left;verticalAlign=top;spacingLeft=8;spacingTop=6;fontSize=11;fontStyle=1;" vertex="1" parent="1"><mxGeometry x="40" y="215" width="1840" height="445" as="geometry"/></mxCell>
        <mxCell id="bg_dao" value="«package» com.taller.dao" style="text;html=1;strokeColor=#82b366;fillColor=#f0fff0;align=left;verticalAlign=top;spacingLeft=8;spacingTop=6;fontSize=11;fontStyle=1;" vertex="1" parent="1"><mxGeometry x="40" y="685" width="1840" height="315" as="geometry"/></mxCell>
        <mxCell id="bg_ui" value="«package» com.taller.ui" style="text;html=1;strokeColor=#d6b656;fillColor=#fffef0;align=left;verticalAlign=top;spacingLeft=8;spacingTop=6;fontSize=11;fontStyle=1;" vertex="1" parent="1"><mxGeometry x="40" y="1025" width="1840" height="365" as="geometry"/></mxCell>
        <mxCell id="bg_util" value="«package» com.taller.util" style="text;html=1;strokeColor=#9673a6;fillColor=#f9f0ff;align=left;verticalAlign=top;spacingLeft=8;spacingTop=6;fontSize=11;fontStyle=1;" vertex="1" parent="1"><mxGeometry x="40" y="1415" width="730" height="225" as="geometry"/></mxCell>

        <!-- ===== com.taller.Main ===== -->
        <mxCell id="c_main" value="Main" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1"><mxGeometry x="60" y="60" width="315" height="110" as="geometry"/></mxCell>
        <mxCell id="c_main_m" value="+ main(args: String[]): void  {static}" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_main"><mxGeometry y="26" width="315" height="84" as="geometry"/></mxCell>

        <!-- ===== MODELO ===== -->
        <mxCell id="c_persona" value="«Abstract»  Persona" style="swimlane;fontStyle=3;align=center;startSize=30;swimlaneLine=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1"><mxGeometry x="60" y="255" width="215" height="190" as="geometry"/></mxCell>
        <mxCell id="c_persona_a" value="# id: int&#xa;# nombre: String&#xa;# telefono: String&#xa;# email: String" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_persona"><mxGeometry y="30" width="215" height="74" as="geometry"/></mxCell>
        <mxCell id="c_persona_m" value="+ resumen(): String  {abstract}&#xa;+ getId(): int&#xa;+ getNombre(): String" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_persona"><mxGeometry y="104" width="215" height="86" as="geometry"/></mxCell>

        <mxCell id="c_cliente" value="Cliente" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="60" y="472" width="210" height="130" as="geometry"/></mxCell>
        <mxCell id="c_cliente_a" value="- direccion: String" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_cliente"><mxGeometry y="26" width="210" height="50" as="geometry"/></mxCell>
        <mxCell id="c_cliente_m" value="+ resumen(): String&#xa;+ getDireccion(): String" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_cliente"><mxGeometry y="76" width="210" height="54" as="geometry"/></mxCell>

        <mxCell id="c_mecanico" value="Mecanico" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="290" y="472" width="220" height="140" as="geometry"/></mxCell>
        <mxCell id="c_mecanico_a" value="- especialidad: String&#xa;- disponible: boolean" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_mecanico"><mxGeometry y="26" width="220" height="50" as="geometry"/></mxCell>
        <mxCell id="c_mecanico_m" value="+ resumen(): String&#xa;+ isDisponible(): boolean" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_mecanico"><mxGeometry y="76" width="220" height="64" as="geometry"/></mxCell>

        <mxCell id="c_item_base" value="«Abstract»  ItemPresupuesto" style="swimlane;fontStyle=3;align=center;startSize=30;swimlaneLine=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="545" y="255" width="245" height="190" as="geometry"/></mxCell>
        <mxCell id="c_item_base_a" value="# id: int&#xa;# ordenId: int&#xa;# tipo: String&#xa;# descripcion: String" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_item_base"><mxGeometry y="30" width="245" height="74" as="geometry"/></mxCell>
        <mxCell id="c_item_base_m" value="+ calcularSubtotal(): double  {abstract}&#xa;+ getDescripcion(): String" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_item_base"><mxGeometry y="104" width="245" height="86" as="geometry"/></mxCell>

        <mxCell id="c_item_ref" value="ItemRefaccion" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="525" y="472" width="225" height="140" as="geometry"/></mxCell>
        <mxCell id="c_item_ref_a" value="- refaccionId: int&#xa;- precioUnitario: double&#xa;- cantidad: int" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_item_ref"><mxGeometry y="26" width="225" height="68" as="geometry"/></mxCell>
        <mxCell id="c_item_ref_m" value="+ calcularSubtotal(): double" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_item_ref"><mxGeometry y="94" width="225" height="46" as="geometry"/></mxCell>

        <mxCell id="c_item_mano" value="ItemManoObra" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="770" y="472" width="220" height="130" as="geometry"/></mxCell>
        <mxCell id="c_item_mano_a" value="- costoFijo: double&#xa;- horas: double" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_item_mano"><mxGeometry y="26" width="220" height="50" as="geometry"/></mxCell>
        <mxCell id="c_item_mano_m" value="+ calcularSubtotal(): double" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_item_mano"><mxGeometry y="76" width="220" height="54" as="geometry"/></mxCell>

        <mxCell id="c_usuario" value="Usuario" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1"><mxGeometry x="1020" y="255" width="225" height="195" as="geometry"/></mxCell>
        <mxCell id="c_usuario_a" value="- id: int&#xa;- username: String&#xa;- claveHash: String&#xa;- rol: RolUsuario&#xa;- personaId: Integer" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_usuario"><mxGeometry y="26" width="225" height="108" as="geometry"/></mxCell>
        <mxCell id="c_usuario_m" value="+ verificarClave(txt: String): boolean" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_usuario"><mxGeometry y="134" width="225" height="61" as="geometry"/></mxCell>

        <mxCell id="enum_rol" value="«Enumeration»  RolUsuario" style="swimlane;fontStyle=1;align=center;startSize=30;swimlaneLine=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1"><mxGeometry x="1040" y="475" width="190" height="150" as="geometry"/></mxCell>
        <mxCell id="enum_rol_v" value="SUPERADMIN&#xa;GERENTE&#xa;MECANICO&#xa;EMPLEADO&#xa;CLIENTE" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="enum_rol"><mxGeometry y="30" width="190" height="120" as="geometry"/></mxCell>

        <mxCell id="c_vehiculo" value="Vehiculo" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1"><mxGeometry x="1275" y="255" width="230" height="195" as="geometry"/></mxCell>
        <mxCell id="c_vehiculo_a" value="- id: int&#xa;- placas: String&#xa;- marca / modelo / color: String&#xa;- clienteId: int&#xa;- estatus: EstatusVehiculo&#xa;- rutaFoto: String" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_vehiculo"><mxGeometry y="26" width="230" height="169" as="geometry"/></mxCell>

        <mxCell id="c_orden" value="OrdenReparacion" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1"><mxGeometry x="1530" y="255" width="250" height="205" as="geometry"/></mxCell>
        <mxCell id="c_orden_a" value="- id: int&#xa;- vehiculoId: int&#xa;- mecanicoId: Integer&#xa;- fechaIngreso: LocalDateTime&#xa;- estatus: EstatusVehiculo&#xa;- items: List&lt;ItemPresupuesto&gt;" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_orden"><mxGeometry y="26" width="250" height="179" as="geometry"/></mxCell>

        <mxCell id="enum_estatus" value="«Enumeration»  EstatusVehiculo" style="swimlane;fontStyle=1;align=center;startSize=30;swimlaneLine=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1"><mxGeometry x="1275" y="475" width="215" height="140" as="geometry"/></mxCell>
        <mxCell id="enum_estatus_v" value="EN_REVISION&#xa;ESPERA_PIEZAS&#xa;LISTO&#xa;—&#xa;+ getEtiqueta(): String" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="enum_estatus"><mxGeometry y="30" width="215" height="110" as="geometry"/></mxCell>

        <mxCell id="c_refaccion" value="Refaccion" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="1510" y="475" width="215" height="145" as="geometry"/></mxCell>
        <mxCell id="c_refaccion_a" value="- id: int&#xa;- nombre: String&#xa;- precioUnitario: double&#xa;- stock: int&#xa;- rutaFoto: String" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_refaccion"><mxGeometry y="26" width="215" height="119" as="geometry"/></mxCell>

        <mxCell id="c_bitacora_mod" value="RegistroBitacora" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1"><mxGeometry x="1745" y="475" width="120" height="165" as="geometry"/></mxCell>
        <mxCell id="c_bitacora_mod_a" value="- id: int&#xa;- username: String&#xa;- fechaHora: LocalDateTime&#xa;- accion: String&#xa;- detalle: String" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_bitacora_mod"><mxGeometry y="26" width="120" height="139" as="geometry"/></mxCell>

        <!-- ===== DAO ===== -->
        <mxCell id="dao_conexion" value="ConexionBD" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="60" y="730" width="250" height="130" as="geometry"/></mxCell>
        <mxCell id="dao_conexion_m" value="+ getConexion(): Connection&#xa;+ inicializarEsquema(): void&#xa;+ sembrarDatosDefecto(): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="dao_conexion"><mxGeometry y="26" width="250" height="104" as="geometry"/></mxCell>

        <mxCell id="dao_usuario" value="UsuarioDAO" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="330" y="730" width="240" height="145" as="geometry"/></mxCell>
        <mxCell id="dao_usuario_m" value="+ autenticar(u, c): Usuario&#xa;+ registrar(u, c, r, p): Usuario&#xa;+ existeUsername(u): boolean&#xa;+ listarTodos(): List&lt;Usuario&gt;" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="dao_usuario"><mxGeometry y="26" width="240" height="119" as="geometry"/></mxCell>

        <mxCell id="dao_cliente" value="ClienteDAO" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="590" y="730" width="235" height="130" as="geometry"/></mxCell>
        <mxCell id="dao_cliente_m" value="+ crear(c): int&#xa;+ actualizar(c): void&#xa;+ eliminar(id): void&#xa;+ listarTodos(): List&lt;Cliente&gt;" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="dao_cliente"><mxGeometry y="26" width="235" height="104" as="geometry"/></mxCell>

        <mxCell id="dao_mecanico" value="MecanicoDAO" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="845" y="730" width="235" height="130" as="geometry"/></mxCell>
        <mxCell id="dao_mecanico_m" value="+ crear(m): int&#xa;+ actualizar(m): void&#xa;+ eliminar(id): void&#xa;+ listarTodos(): List&lt;Mecanico&gt;" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="dao_mecanico"><mxGeometry y="26" width="235" height="104" as="geometry"/></mxCell>

        <mxCell id="dao_vehiculo" value="VehiculoDAO" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="1100" y="730" width="245" height="145" as="geometry"/></mxCell>
        <mxCell id="dao_vehiculo_m" value="+ crear(v): int&#xa;+ eliminar(id): void&#xa;+ actualizarEstatus(id, est): void&#xa;+ listarPorCliente(cId): List" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="dao_vehiculo"><mxGeometry y="26" width="245" height="119" as="geometry"/></mxCell>

        <mxCell id="dao_orden" value="OrdenDAO" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="1365" y="730" width="255" height="160" as="geometry"/></mxCell>
        <mxCell id="dao_orden_m" value="+ crear(o): int&#xa;+ listarPorMecanico(mId): List&#xa;+ listarPorVehiculo(vId): List&#xa;+ actualizarEstatus(id, est): void&#xa;+ eliminar(id): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="dao_orden"><mxGeometry y="26" width="255" height="134" as="geometry"/></mxCell>

        <mxCell id="dao_refaccion" value="RefaccionDAO" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="1640" y="730" width="245" height="130" as="geometry"/></mxCell>
        <mxCell id="dao_refaccion_m" value="+ crear(r): int&#xa;+ actualizar(r): void&#xa;+ descontarStock(id, cant): void&#xa;+ listarTodos(): List&lt;Refaccion&gt;" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="dao_refaccion"><mxGeometry y="26" width="245" height="104" as="geometry"/></mxCell>

        <mxCell id="dao_bitacora" value="BitacoraDAO" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1"><mxGeometry x="60" y="875" width="255" height="110" as="geometry"/></mxCell>
        <mxCell id="dao_bitacora_m" value="+ registrar(accion, detalle): void&#xa;+ listarTodos(): List&lt;RegistroBitacora&gt;" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="dao_bitacora"><mxGeometry y="26" width="255" height="84" as="geometry"/></mxCell>

        <!-- ===== UI ===== -->
        <mxCell id="ui_login" value="LoginFrame" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1"><mxGeometry x="60" y="1070" width="220" height="130" as="geometry"/></mxCell>
        <mxCell id="ui_login_a" value="- txtUsuario: JTextField&#xa;- txtClave: JPasswordField" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_login"><mxGeometry y="26" width="220" height="52" as="geometry"/></mxCell>
        <mxCell id="ui_login_m" value="+ intentarLogin(): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_login"><mxGeometry y="78" width="220" height="52" as="geometry"/></mxCell>

        <mxCell id="ui_dash" value="MainDashboard" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1"><mxGeometry x="300" y="1070" width="250" height="150" as="geometry"/></mxCell>
        <mxCell id="ui_dash_a" value="- cardLayout: CardLayout&#xa;- usuarioActual: Usuario" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_dash"><mxGeometry y="26" width="250" height="52" as="geometry"/></mxCell>
        <mxCell id="ui_dash_m" value="+ construirMenuLateral(): void&#xa;+ etiquetaRol(r: RolUsuario): String" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_dash"><mxGeometry y="78" width="250" height="72" as="geometry"/></mxCell>

        <mxCell id="ui_cliente_vista" value="PanelClienteVista" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="570" y="1070" width="240" height="140" as="geometry"/></mxCell>
        <mxCell id="ui_cliente_vista_a" value="- panelVehiculos: JPanel" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_cliente_vista"><mxGeometry y="26" width="240" height="52" as="geometry"/></mxCell>
        <mxCell id="ui_cliente_vista_m" value="+ cargarDatos(): void&#xa;+ crearTarjetaVehiculo(v): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_cliente_vista"><mxGeometry y="78" width="240" height="62" as="geometry"/></mxCell>

        <mxCell id="ui_estatus" value="PanelEstatus" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="830" y="1070" width="200" height="100" as="geometry"/></mxCell>
        <mxCell id="ui_estatus_m" value="+ refrescar(): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_estatus"><mxGeometry y="26" width="200" height="74" as="geometry"/></mxCell>

        <mxCell id="ui_ordenes" value="PanelOrdenes" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="1045" y="1070" width="200" height="100" as="geometry"/></mxCell>
        <mxCell id="ui_ordenes_m" value="+ refrescar(): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_ordenes"><mxGeometry y="26" width="200" height="74" as="geometry"/></mxCell>

        <mxCell id="ui_vehiculos" value="PanelVehiculos" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="1260" y="1070" width="200" height="100" as="geometry"/></mxCell>
        <mxCell id="ui_vehiculos_m" value="+ refrescar(): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_vehiculos"><mxGeometry y="26" width="200" height="74" as="geometry"/></mxCell>

        <mxCell id="ui_personas" value="PanelPersonas" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1"><mxGeometry x="1475" y="1070" width="200" height="100" as="geometry"/></mxCell>
        <mxCell id="ui_personas_m" value="+ refrescar(): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_personas"><mxGeometry y="26" width="200" height="74" as="geometry"/></mxCell>

        <mxCell id="ui_refrescable" value="«Interface»  Refrescable" style="swimlane;fontStyle=3;align=center;startSize=30;swimlaneLine=1;fillColor=#f5f5f5;strokeColor=#666666;" vertex="1" parent="1"><mxGeometry x="1695" y="1070" width="170" height="100" as="geometry"/></mxCell>
        <mxCell id="ui_refrescable_m" value="+ refrescar(): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_refrescable"><mxGeometry y="30" width="170" height="70" as="geometry"/></mxCell>

        <mxCell id="ui_usuarios" value="PanelUsuarios" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1"><mxGeometry x="60" y="1225" width="225" height="120" as="geometry"/></mxCell>
        <mxCell id="ui_usuarios_m" value="+ crearCuenta(): void&#xa;+ refrescar(): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_usuarios"><mxGeometry y="26" width="225" height="94" as="geometry"/></mxCell>

        <mxCell id="ui_refacciones" value="PanelRefacciones" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="305" y="1225" width="230" height="100" as="geometry"/></mxCell>
        <mxCell id="ui_refacciones_m" value="+ refrescar(): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_refacciones"><mxGeometry y="26" width="230" height="74" as="geometry"/></mxCell>

        <mxCell id="ui_bitacora" value="PanelBitacora" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1"><mxGeometry x="555" y="1225" width="215" height="100" as="geometry"/></mxCell>
        <mxCell id="ui_bitacora_m" value="+ refrescar(): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_bitacora"><mxGeometry y="26" width="215" height="74" as="geometry"/></mxCell>

        <mxCell id="ui_inv_dialog" value="InventarioVisualDialog" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="790" y="1225" width="245" height="100" as="geometry"/></mxCell>
        <mxCell id="ui_inv_dialog_m" value="+ guardarInventario(): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_inv_dialog"><mxGeometry y="26" width="245" height="74" as="geometry"/></mxCell>

        <mxCell id="ui_estilos" value="Estilos" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#f5f5f5;strokeColor=#666666;" vertex="1" parent="1"><mxGeometry x="1055" y="1225" width="230" height="125" as="geometry"/></mxCell>
        <mxCell id="ui_estilos_a" value="+ AZUL_OSCURO: Color {static}&#xa;+ VERDE: Color {static}&#xa;+ FUENTE_TITULO: Font {static}" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_estilos"><mxGeometry y="26" width="230" height="99" as="geometry"/></mxCell>

        <mxCell id="ui_boton" value="BotonEstilizado" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#f5f5f5;strokeColor=#666666;" vertex="1" parent="1"><mxGeometry x="1305" y="1225" width="225" height="100" as="geometry"/></mxCell>
        <mxCell id="ui_boton_m" value="+ BotonEstilizado(txt, col): void" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="ui_boton"><mxGeometry y="26" width="225" height="74" as="geometry"/></mxCell>

        <!-- ===== UTIL ===== -->
        <mxCell id="c_sesion" value="Sesion  {Singleton}" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1"><mxGeometry x="60" y="1460" width="255" height="155" as="geometry"/></mxCell>
        <mxCell id="c_sesion_a" value="- instancia: Sesion {static}&#xa;- usuarioActual: Usuario" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_sesion"><mxGeometry y="26" width="255" height="52" as="geometry"/></mxCell>
        <mxCell id="c_sesion_m" value="+ getInstancia(): Sesion {static}&#xa;+ iniciarSesion(u: Usuario): void&#xa;+ cerrarSesion(): void&#xa;+ getUsuarioActual(): Usuario" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_sesion"><mxGeometry y="78" width="255" height="77" as="geometry"/></mxCell>

        <mxCell id="c_seguridad" value="SeguridadUtil" style="swimlane;fontStyle=1;align=center;startSize=26;swimlaneLine=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1"><mxGeometry x="335" y="1460" width="270" height="120" as="geometry"/></mxCell>
        <mxCell id="c_seguridad_m" value="+ hashSHA256(txt: String): String {static}&#xa;+ validarEmail(em: String): boolean {static}" style="text;strokeColor=inherit;fillColor=none;align=left;verticalAlign=top;spacingLeft=4;overflow=hidden;rotatable=0;" vertex="1" parent="c_seguridad"><mxGeometry y="26" width="270" height="94" as="geometry"/></mxCell>

        <!-- ===== RELACIONES ===== -->

        <!-- GENERALIZACIÓN — línea sólida, triángulo HUECO apuntando a superclase -->
        <mxCell id="h_cli" style="endArrow=block;endFill=0;html=1;strokeWidth=2;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;" edge="1" parent="1" source="c_cliente" target="c_persona"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="h_mec" style="endArrow=block;endFill=0;html=1;strokeWidth=2;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;" edge="1" parent="1" source="c_mecanico" target="c_persona"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="h_ir" style="endArrow=block;endFill=0;html=1;strokeWidth=2;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;" edge="1" parent="1" source="c_item_ref" target="c_item_base"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="h_im" style="endArrow=block;endFill=0;html=1;strokeWidth=2;exitX=0.5;exitY=0;exitDx=0;exitDy=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;" edge="1" parent="1" source="c_item_mano" target="c_item_base"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- REALIZACIÓN DE INTERFAZ — línea DISCONTINUA, triángulo HUECO hacia la interfaz -->
        <mxCell id="r_est" style="endArrow=block;endFill=0;dashed=1;dashPattern=8 3;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_estatus" target="ui_refrescable"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="r_ord" style="endArrow=block;endFill=0;dashed=1;dashPattern=8 3;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_ordenes" target="ui_refrescable"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="r_veh" style="endArrow=block;endFill=0;dashed=1;dashPattern=8 3;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_vehiculos" target="ui_refrescable"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="r_per" style="endArrow=block;endFill=0;dashed=1;dashPattern=8 3;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_personas" target="ui_refrescable"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="r_usu" style="endArrow=block;endFill=0;dashed=1;dashPattern=8 3;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_usuarios" target="ui_refrescable"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="r_ref" style="endArrow=block;endFill=0;dashed=1;dashPattern=8 3;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_refacciones" target="ui_refrescable"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="r_bit" style="endArrow=block;endFill=0;dashed=1;dashPattern=8 3;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_bitacora" target="ui_refrescable"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- ASOCIACIONES — línea sólida, punta abierta -->
        <mxCell id="a_usr_rol" value="usa" style="endArrow=open;endSize=10;html=1;strokeWidth=1.5;" edge="1" parent="1" source="c_usuario" target="enum_rol"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="a_veh_est" value="usa" style="endArrow=open;endSize=10;html=1;strokeWidth=1.5;" edge="1" parent="1" source="c_vehiculo" target="enum_estatus"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="a_ord_est" value="usa" style="endArrow=open;endSize=10;html=1;strokeWidth=1.5;" edge="1" parent="1" source="c_orden" target="enum_estatus"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="a_veh_cli" value="pertenece a" style="endArrow=open;endSize=10;html=1;strokeWidth=1.5;" edge="1" parent="1" source="c_vehiculo" target="c_cliente"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="a_ord_veh" value="es de" style="endArrow=open;endSize=10;html=1;strokeWidth=1.5;" edge="1" parent="1" source="c_orden" target="c_vehiculo"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="a_ord_mec" value="asignado a 0..1" style="endArrow=open;endSize=10;html=1;strokeWidth=1.5;" edge="1" parent="1" source="c_orden" target="c_mecanico"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- COMPOSICIÓN — diamante RELLENO en el "todo", punta abierta en la "parte" -->
        <mxCell id="comp_items" value="  items 0..*" style="startArrow=diamond;startFill=1;startSize=10;endArrow=open;endSize=8;html=1;strokeWidth=1.5;" edge="1" parent="1" source="c_orden" target="c_item_base"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- Referencia ItemRefaccion → Refaccion (dependencia de tipo) -->
        <mxCell id="a_ir_ref" value="referencia" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="c_item_ref" target="c_refaccion"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- DEPENDENCIAS — línea discontinua, punta abierta, con estereotipo -->
        <mxCell id="d_main_c" value="«call»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;strokeColor=#b85450;" edge="1" parent="1" source="c_main" target="dao_conexion"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_main_u" value="«call»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;strokeColor=#b85450;" edge="1" parent="1" source="c_main" target="dao_usuario"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_main_l" value="«instantiate»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;strokeColor=#b85450;" edge="1" parent="1" source="c_main" target="ui_login"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_log_dash" value="«instantiate»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_login" target="ui_dash"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_log_ses" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_login" target="c_sesion"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_log_seg" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_login" target="c_seguridad"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_log_ud" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_login" target="dao_usuario"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_das_ses" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="ui_dash" target="c_sesion"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- Todos los DAOs dependen de ConexionBD -->
        <mxCell id="dc1" value="«use»" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="dao_usuario" target="dao_conexion"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="dc2" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="dao_cliente" target="dao_conexion"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="dc3" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="dao_mecanico" target="dao_conexion"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="dc4" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="dao_vehiculo" target="dao_conexion"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="dc5" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="dao_orden" target="dao_conexion"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="dc6" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="dao_refaccion" target="dao_conexion"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="dc7" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="dao_bitacora" target="dao_conexion"><mxGeometry relative="1" as="geometry"/></mxCell>

      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
```

---

## 3. Diagrama de Componentes Completo (UML Oficial)

Representa la división formal física del software mediante componentes y especifica los puntos de comunicación a través de interfaces provistas y requeridas (Lollipops y Sockets).

```xml
<mxfile host="app.diagrams.net">
  <diagram name="Diagrama de Componentes UML — Hotwheels Tam">
    <mxGraphModel dx="1422" dy="900" grid="1" gridSize="10" guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="1" pageScale="1" pageWidth="1620" pageHeight="1160" math="0" shadow="0">
      <root>
        <mxCell id="0"/><mxCell id="1" parent="0"/>

        <!-- ===== PAQUETES UML (tab + cuerpo, sin contener elementos) ===== -->

        <!-- Tab y cuerpo: com.taller (Arranque) -->
        <mxCell id="tab0" value="«package» com.taller" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#b85450;strokeColor=#b85450;fontColor=#ffffff;fontStyle=1;fontSize=10;align=left;spacingLeft=6;" vertex="1" parent="1"><mxGeometry x="40" y="40" width="210" height="22" as="geometry"/></mxCell>
        <mxCell id="body0" value="" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#fdecea;strokeColor=#b85450;" vertex="1" parent="1"><mxGeometry x="40" y="60" width="1540" height="115" as="geometry"/></mxCell>

        <!-- Tab y cuerpo: com.taller.ui (Presentación) -->
        <mxCell id="tab1" value="«package» com.taller.ui  —  Capa de Presentación (Swing GUI)" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#6c8ebf;strokeColor=#6c8ebf;fontColor=#ffffff;fontStyle=1;fontSize=10;align=left;spacingLeft=6;" vertex="1" parent="1"><mxGeometry x="40" y="195" width="380" height="22" as="geometry"/></mxCell>
        <mxCell id="body1" value="" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#ebf3fb;strokeColor=#6c8ebf;" vertex="1" parent="1"><mxGeometry x="40" y="215" width="1540" height="290" as="geometry"/></mxCell>

        <!-- Tab y cuerpo: com.taller.dao + util (Lógica) -->
        <mxCell id="tab2" value="«package» com.taller.dao + com.taller.util  —  Lógica de Negocio y Acceso a Datos" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#82b366;strokeColor=#82b366;fontColor=#ffffff;fontStyle=1;fontSize=10;align=left;spacingLeft=6;" vertex="1" parent="1"><mxGeometry x="40" y="530" width="470" height="22" as="geometry"/></mxCell>
        <mxCell id="body2" value="" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#eaf5ea;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="40" y="550" width="1540" height="280" as="geometry"/></mxCell>

        <!-- Tab y cuerpo: Persistencia -->
        <mxCell id="tab3" value="«package» Capa de Persistencia  —  ConexionBD (JDBC) + MySQL" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#9673a6;strokeColor=#9673a6;fontColor=#ffffff;fontStyle=1;fontSize=10;align=left;spacingLeft=6;" vertex="1" parent="1"><mxGeometry x="40" y="860" width="390" height="22" as="geometry"/></mxCell>
        <mxCell id="body3" value="" style="rounded=0;whiteSpace=wrap;html=1;fillColor=#f5eff9;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="40" y="880" width="1540" height="250" as="geometry"/></mxCell>

        <!-- ===== CAPA 0: ARRANQUE — Main ===== -->
        <mxCell id="cmp_main" value="«component»&#xa;MainLauncher&#xa;(com.taller.Main)" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;fontStyle=1;" vertex="1" parent="1"><mxGeometry x="80" y="78" width="300" height="78" as="geometry"/></mxCell>

        <!-- ===== CAPA 1: GUI ===== -->
        <mxCell id="cmp_login" value="«component»&#xa;LoginFrame" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;fontStyle=1;" vertex="1" parent="1"><mxGeometry x="80" y="250" width="200" height="70" as="geometry"/></mxCell>

        <mxCell id="cmp_dash" value="«component»&#xa;MainDashboard" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;fontStyle=1;" vertex="1" parent="1"><mxGeometry x="305" y="250" width="215" height="70" as="geometry"/></mxCell>

        <mxCell id="cmp_cli_vista" value="«component»&#xa;PanelClienteVista&#xa;(Solo Rol CLIENTE)" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;fontStyle=1;" vertex="1" parent="1"><mxGeometry x="545" y="250" width="220" height="70" as="geometry"/></mxCell>

        <mxCell id="cmp_op" value="«component»&#xa;Módulos Operativos&#xa;PanelEstatus | PanelVehiculos&#xa;PanelOrdenes | PanelRefacciones" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="795" y="240" width="280" height="90" as="geometry"/></mxCell>

        <mxCell id="cmp_adm" value="«component»&#xa;Módulos Administrativos&#xa;PanelUsuarios | PanelPersonas&#xa;PanelBitacora" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1"><mxGeometry x="1105" y="240" width="275" height="90" as="geometry"/></mxCell>

        <mxCell id="cmp_inv" value="«component»&#xa;InventarioVisualDialog" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1"><mxGeometry x="795" y="355" width="280" height="60" as="geometry"/></mxCell>

        <mxCell id="cmp_uicore" value="«component»&#xa;UICore&#xa;Estilos | BotonEstilizado" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#f5f5f5;strokeColor=#666666;" vertex="1" parent="1"><mxGeometry x="1405" y="340" width="155" height="80" as="geometry"/></mxCell>

        <!-- Refrescable (Interfaz en capa UI) -->
        <mxCell id="if_refrescable_box" value="«interface»&#xa;Refrescable&#xa;+ refrescar(): void" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#f5f5f5;strokeColor=#666666;fontStyle=3;" vertex="1" parent="1"><mxGeometry x="1405" y="250" width="155" height="75" as="geometry"/></mxCell>

        <!-- ===== CAPA 2: LÓGICA / DAO ===== -->
        <mxCell id="cmp_seg" value="«component»&#xa;SeguridadUtil&#xa;(SHA-256 / Regex)" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1"><mxGeometry x="80" y="590" width="200" height="75" as="geometry"/></mxCell>

        <mxCell id="cmp_ses" value="«component»&#xa;Sesion  {Singleton}&#xa;(Usuario Actual)" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1"><mxGeometry x="305" y="590" width="215" height="75" as="geometry"/></mxCell>

        <mxCell id="cmp_udao" value="«component»&#xa;UsuarioDAO" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="545" y="590" width="200" height="70" as="geometry"/></mxCell>

        <mxCell id="cmp_pdao" value="«component»&#xa;ClienteDAO&#xa;MecanicoDAO" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="770" y="590" width="200" height="75" as="geometry"/></mxCell>

        <mxCell id="cmp_vdao" value="«component»&#xa;VehiculoDAO" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="995" y="590" width="200" height="70" as="geometry"/></mxCell>

        <mxCell id="cmp_odao" value="«component»&#xa;OrdenDAO" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="1220" y="590" width="200" height="70" as="geometry"/></mxCell>

        <mxCell id="cmp_rdao" value="«component»&#xa;RefaccionDAO" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1"><mxGeometry x="80" y="685" width="200" height="70" as="geometry"/></mxCell>

        <mxCell id="cmp_bdao" value="«component»&#xa;BitacoraDAO&#xa;(Transversal — Auditoría)" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;fontStyle=1;" vertex="1" parent="1"><mxGeometry x="305" y="685" width="250" height="75" as="geometry"/></mxCell>

        <!-- ===== CAPA 3: PERSISTENCIA ===== -->
        <mxCell id="cmp_con" value="«component»&#xa;ConexionBD&#xa;(JDBC Driver MySQL)" style="shape=component;align=center;spacingLeft=36;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;fontStyle=1;" vertex="1" parent="1"><mxGeometry x="80" y="920" width="270" height="80" as="geometry"/></mxCell>

        <mxCell id="db_mysql" value="&lt;b&gt;«database»  hotwheels_tam&lt;/b&gt;&#xa;usuarios | clientes | mecanicos&#xa;vehiculos | ordenes | items_presupuesto&#xa;refacciones | bitacora" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;fillColor=#e1d5e7;strokeColor=#9673a6;align=center;verticalAlign=top;spacingTop=8;" vertex="1" parent="1"><mxGeometry x="400" y="900" width="1140" height="200" as="geometry"/></mxCell>

        <!-- ===== INTERFACES PROVISTAS (Lollipops — círculo pequeño) ===== -->
        <!-- ConexionBD provee IJDBCConexion -->
        <mxCell id="lp_jdbc" value="IJDBCConexion" style="ellipse;whiteSpace=wrap;html=1;aspect=fixed;fillColor=#ffffff;strokeColor=#9673a6;fontSize=8;verticalLabelPosition=bottom;verticalAlign=top;" vertex="1" parent="1"><mxGeometry x="342" y="950" width="22" height="22" as="geometry"/></mxCell>
        <mxCell id="line_jdbc" style="endArrow=none;html=1;strokeColor=#9673a6;" edge="1" parent="1" source="cmp_con" target="lp_jdbc"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- UsuarioDAO provee IAutenticacion -->
        <mxCell id="lp_auth" value="IAutenticacion" style="ellipse;whiteSpace=wrap;html=1;aspect=fixed;fillColor=#ffffff;strokeColor=#9673a6;fontSize=8;verticalLabelPosition=bottom;verticalAlign=top;" vertex="1" parent="1"><mxGeometry x="637" y="572" width="22" height="22" as="geometry"/></mxCell>
        <mxCell id="line_auth" style="endArrow=none;html=1;strokeColor=#9673a6;" edge="1" parent="1" source="cmp_udao" target="lp_auth"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- Sesion provee ISession -->
        <mxCell id="lp_ses" value="ISession" style="ellipse;whiteSpace=wrap;html=1;aspect=fixed;fillColor=#ffffff;strokeColor=#6c8ebf;fontSize=8;verticalLabelPosition=bottom;verticalAlign=top;" vertex="1" parent="1"><mxGeometry x="407" y="572" width="22" height="22" as="geometry"/></mxCell>
        <mxCell id="line_ses" style="endArrow=none;html=1;strokeColor=#6c8ebf;" edge="1" parent="1" source="cmp_ses" target="lp_ses"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- SeguridadUtil provee ISeguridadUtil -->
        <mxCell id="lp_seg" value="ISeguridadUtil" style="ellipse;whiteSpace=wrap;html=1;aspect=fixed;fillColor=#ffffff;strokeColor=#b85450;fontSize=8;verticalLabelPosition=bottom;verticalAlign=top;" vertex="1" parent="1"><mxGeometry x="172" y="572" width="22" height="22" as="geometry"/></mxCell>
        <mxCell id="line_seg" style="endArrow=none;html=1;strokeColor=#b85450;" edge="1" parent="1" source="cmp_seg" target="lp_seg"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- BitacoraDAO provee IBitacoraDAO -->
        <mxCell id="lp_bit" value="IBitacoraDAO" style="ellipse;whiteSpace=wrap;html=1;aspect=fixed;fillColor=#ffffff;strokeColor=#d6b656;fontSize=8;verticalLabelPosition=bottom;verticalAlign=top;" vertex="1" parent="1"><mxGeometry x="547" y="695" width="22" height="22" as="geometry"/></mxCell>
        <mxCell id="line_bit" style="endArrow=none;html=1;strokeColor=#d6b656;" edge="1" parent="1" source="cmp_bdao" target="lp_bit"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- ===== DEPENDENCIAS (flechas UML estándar) ===== -->
        <!-- Main → lanza LoginFrame -->
        <mxCell id="d_main_login" value="«instantiate»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;strokeColor=#b85450;" edge="1" parent="1" source="cmp_main" target="cmp_login"><mxGeometry relative="1" as="geometry"/></mxCell>
        <!-- Main → usa ConexionBD (a través de su interfaz) -->
        <mxCell id="d_main_jdbc" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;strokeColor=#b85450;" edge="1" parent="1" source="cmp_main" target="lp_jdbc"><mxGeometry relative="1" as="geometry"/></mxCell>
        <!-- Main → usa UsuarioDAO -->
        <mxCell id="d_main_auth" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;strokeColor=#b85450;" edge="1" parent="1" source="cmp_main" target="lp_auth"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- LoginFrame →→ -->
        <mxCell id="d_log_dash" value="«instantiate»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="cmp_login" target="cmp_dash"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_log_ses" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="cmp_login" target="lp_ses"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_log_auth" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="cmp_login" target="lp_auth"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_log_seg" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="cmp_login" target="lp_seg"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- Dashboard → Sesion y carga paneles -->
        <mxCell id="d_dash_ses" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="cmp_dash" target="lp_ses"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_dash_op" value="«navigates»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="cmp_dash" target="cmp_op"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_dash_adm" value="«navigates»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="cmp_dash" target="cmp_adm"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_dash_cli" value="«navigates»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1.5;" edge="1" parent="1" source="cmp_dash" target="cmp_cli_vista"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- Paneles operativos → DAOs operativos -->
        <mxCell id="d_op_vdao" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_op" target="cmp_vdao"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_op_odao" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_op" target="cmp_odao"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_op_rdao" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_op" target="cmp_rdao"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- Paneles admin → DAOs admin -->
        <mxCell id="d_adm_udao" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_adm" target="cmp_udao"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_adm_pdao" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_adm" target="cmp_pdao"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_adm_bit" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_adm" target="lp_bit"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="d_op_bit" value="«use»" style="endArrow=open;endSize=10;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_op" target="lp_bit"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- Implementan Refrescable -->
        <mxCell id="r_op_ref" style="endArrow=block;endFill=0;dashed=1;dashPattern=8 3;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_op" target="if_refrescable_box"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="r_adm_ref" style="endArrow=block;endFill=0;dashed=1;dashPattern=8 3;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_adm" target="if_refrescable_box"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- Todos los DAOs → IJDBCConexion (ConexionBD) -->
        <mxCell id="dc_u" value="«use»" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_udao" target="lp_jdbc"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="dc_p" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_pdao" target="lp_jdbc"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="dc_v" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_vdao" target="lp_jdbc"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="dc_o" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_odao" target="lp_jdbc"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="dc_r" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_rdao" target="lp_jdbc"><mxGeometry relative="1" as="geometry"/></mxCell>
        <mxCell id="dc_b" style="endArrow=open;endSize=8;dashed=1;html=1;strokeWidth=1;" edge="1" parent="1" source="cmp_bdao" target="lp_jdbc"><mxGeometry relative="1" as="geometry"/></mxCell>

        <!-- ConexionBD → MySQL DB -->
        <mxCell id="d_con_db" value="JDBC SQL" style="endArrow=block;endFill=1;html=1;strokeWidth=2;strokeColor=#9673a6;" edge="1" parent="1" source="cmp_con" target="db_mysql"><mxGeometry relative="1" as="geometry"/></mxCell>

      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
```
