# Diagramas del Sistema de Taller Mecánico (Hotwheels Tam)
## Formato XML compatible con Draw.io (diagrams.net)

Para visualizarlos en Draw.io:
1. Abre [draw.io](https://app.diagrams.net).
2. Ve a **Menú > Extras > Insertar > Avanzado > XML**.
3. Pega el bloque de código de cada diagrama y da clic en **Insertar**.

---

## 1. Diagrama de Casos de Uso Completo

Cubre todos los actores reales del sistema (**SUPERADMIN**, **GERENTE**, **EMPLEADO**, **MECÁNICO**, **CLIENTE**) y todas las funcionalidades actuales.

```xml
<mxfile host="app.diagrams.net">
  <diagram name="Casos de Uso Completo - Hotwheels Tam">
    <mxGraphModel dx="1200" dy="800" grid="1" gridSize="10" guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="1" pageScale="1" pageWidth="1400" pageHeight="1100" math="0" shadow="0">
      <root>
        <mxCell id="0" />
        <mxCell id="1" parent="0" />
        
        <!-- Contenedor del Sistema -->
        <mxCell id="sys" value="Sistema de Gestión Hotwheels Tam" style="shape=rect;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fontStyle=1;fontSize=16;fillColor=#f8f9fa;fontColor=#1f2123;strokeColor=#3d3f43;" vertex="1" parent="1">
          <mxGeometry x="300" y="30" width="760" height="980" as="geometry" />
        </mxCell>

        <!-- ACTORES -->
        <mxCell id="act_superadmin" value="SuperAdmin" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="80" y="80" width="50" height="90" as="geometry" />
        </mxCell>

        <mxCell id="act_gerente" value="Gerente" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="80" y="270" width="50" height="90" as="geometry" />
        </mxCell>

        <mxCell id="act_empleado" value="Empleado / Recepción" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="80" y="470" width="50" height="90" as="geometry" />
        </mxCell>

        <mxCell id="act_mecanico" value="Mecánico" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1">
          <mxGeometry x="80" y="670" width="50" height="90" as="geometry" />
        </mxCell>

        <mxCell id="act_cliente" value="Cliente" style="shape=umlActor;verticalLabelPosition=bottom;verticalAlign=top;html=1;outlineConnect=0;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1">
          <mxGeometry x="80" y="860" width="50" height="90" as="geometry" />
        </mxCell>

        <!-- CASOS DE USO -->
        <!-- Autenticacion -->
        <mxCell id="uc_login" value="Iniciar Sesión / Autenticar" style="ellipse;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;fontStyle=1;" vertex="1" parent="1">
          <mxGeometry x="340" y="70" width="210" height="60" as="geometry" />
        </mxCell>

        <!-- Usuarios -->
        <mxCell id="uc_usuarios_all" value="Gestionar Todos los Usuarios&#xa;(Crear SuperAdmin, Gerente, etc.)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="610" y="70" width="220" height="65" as="geometry" />
        </mxCell>

        <mxCell id="uc_usuarios_limited" value="Gestionar Usuarios Limitados&#xa;(Gerente: No SA/Gerente | Empleado: Solo Cliente)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="610" y="155" width="260" height="65" as="geometry" />
        </mxCell>

        <!-- Bitacora / Auditoria -->
        <mxCell id="uc_bitacora" value="Consultar Bitácora / Auditoría&#xa;de Eventos" style="ellipse;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="340" y="155" width="220" height="65" as="geometry" />
        </mxCell>

        <mxCell id="uc_reg_bitacora" value="Registrar Eventos en Bitácora" style="ellipse;whiteSpace=wrap;html=1;fillColor=#f5f5f5;strokeColor=#666666;fontStyle=2;" vertex="1" parent="1">
          <mxGeometry x="840" y="470" width="190" height="60" as="geometry" />
        </mxCell>

        <!-- Personas -->
        <mxCell id="uc_personas" value="Gestionar Clientes y Mecánicos&#xa;(CRUD Personas)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="340" y="250" width="230" height="65" as="geometry" />
        </mxCell>

        <!-- Vehiculos -->
        <mxCell id="uc_vehiculos" value="Recepción de Vehículos&#xa;y Foto de Evidencia" style="ellipse;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="340" y="345" width="220" height="65" as="geometry" />
        </mxCell>

        <mxCell id="uc_inv_visual" value="Registrar Inventario Visual&#xa;(Daños en carrocería)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="610" y="345" width="220" height="65" as="geometry" />
        </mxCell>

        <!-- Ordenes -->
        <mxCell id="uc_crear_orden" value="Crear Orden de Reparación" style="ellipse;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="340" y="440" width="220" height="65" as="geometry" />
        </mxCell>

        <mxCell id="uc_ordenes_mecanico" value="Consultar Mis Órdenes Asignadas" style="ellipse;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1">
          <mxGeometry x="340" y="535" width="230" height="65" as="geometry" />
        </mxCell>

        <mxCell id="uc_estatus_orden" value="Actualizar Estatus de Orden y Vehículo&#xa;(EN_REVISION, ESPERA_PIEZAS, LISTO)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1">
          <mxGeometry x="600" y="535" width="250" height="65" as="geometry" />
        </mxCell>

        <mxCell id="uc_presupuesto" value="Agregar Refacción o Mano de Obra&#xa;al Presupuesto (Descuenta Stock)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="1">
          <mxGeometry x="600" y="625" width="250" height="65" as="geometry" />
        </mxCell>

        <mxCell id="uc_eliminar_orden" value="Eliminar Orden o Ítem de Presupuesto&#xa;(Solo SuperAdmin / Gerente)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="600" y="715" width="250" height="65" as="geometry" />
        </mxCell>

        <!-- Refacciones -->
        <mxCell id="uc_refacciones" value="Gestionar Inventario de Refacciones&#xa;(Ver, Agregar Stock, Editar Precios)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="340" y="715" width="230" height="65" as="geometry" />
        </mxCell>

        <!-- Cliente Vista -->
        <mxCell id="uc_cliente_vista" value="Consultar Estatus de Mi Vehículo&#xa;(Vista de Solo Lectura y Comentarios)" style="ellipse;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;fontStyle=1;" vertex="1" parent="1">
          <mxGeometry x="340" y="860" width="240" height="70" as="geometry" />
        </mxCell>

        <!-- CONEXIONES -->
        <!-- SuperAdmin -->
        <mxCell id="e_sa1" edge="1" parent="1" source="act_superadmin" target="uc_login"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_sa2" edge="1" parent="1" source="act_superadmin" target="uc_usuarios_all"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_sa3" edge="1" parent="1" source="act_superadmin" target="uc_bitacora"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_sa4" edge="1" parent="1" source="act_superadmin" target="uc_eliminar_orden"><mxGeometry relative="1" as="geometry" /></mxCell>

        <!-- Gerente -->
        <mxCell id="e_g1" edge="1" parent="1" source="act_gerente" target="uc_login"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_g2" edge="1" parent="1" source="act_gerente" target="uc_usuarios_limited"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_g3" edge="1" parent="1" source="act_gerente" target="uc_bitacora"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_g4" edge="1" parent="1" source="act_gerente" target="uc_personas"><mxGeometry relative="1" as="geometry" /></mxCell>

        <!-- Empleado -->
        <mxCell id="e_emp1" edge="1" parent="1" source="act_empleado" target="uc_login"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_emp2" edge="1" parent="1" source="act_empleado" target="uc_vehiculos"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_emp3" edge="1" parent="1" source="act_empleado" target="uc_crear_orden"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_emp4" edge="1" parent="1" source="act_empleado" target="uc_refacciones"><mxGeometry relative="1" as="geometry" /></mxCell>

        <!-- Mecanico -->
        <mxCell id="e_mec1" edge="1" parent="1" source="act_mecanico" target="uc_login"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_mec2" edge="1" parent="1" source="act_mecanico" target="uc_ordenes_mecanico"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_mec3" edge="1" parent="1" source="act_mecanico" target="uc_estatus_orden"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_mec4" edge="1" parent="1" source="act_mecanico" target="uc_presupuesto"><mxGeometry relative="1" as="geometry" /></mxCell>

        <!-- Cliente -->
        <mxCell id="e_cli1" edge="1" parent="1" source="act_cliente" target="uc_login"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="e_cli2" edge="1" parent="1" source="act_cliente" target="uc_cliente_vista"><mxGeometry relative="1" as="geometry" /></mxCell>

        <!-- Includes a Auditoria e inventario -->
        <mxCell id="inc_inv" value="&lt;&lt;include&gt;&gt;" style="endArrow=open;endSize=12;dashed=1;html=1;" edge="1" parent="1" source="uc_vehiculos" target="uc_inv_visual"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="inc_b1" value="&lt;&lt;include&gt;&gt;" style="endArrow=open;endSize=12;dashed=1;html=1;" edge="1" parent="1" source="uc_crear_orden" target="uc_reg_bitacora"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="inc_b2" value="&lt;&lt;include&gt;&gt;" style="endArrow=open;endSize=12;dashed=1;html=1;" edge="1" parent="1" source="uc_estatus_orden" target="uc_reg_bitacora"><mxGeometry relative="1" as="geometry" /></mxCell>
      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
```

---

## 2. Diagrama de Clases Completo (Modelos, DAOs, UI y Jerarquías POO)

Representa **todas las 28 clases Java reales** organizadas por capas (Modelos, Capa DAO y Capa de Interfaz), mostrando Herencia, Encapsulamiento, Polimorfismo y Composición.

```xml
<mxfile host="app.diagrams.net">
  <diagram name="Diagrama de Clases Completo - POO Hotwheels Tam">
    <mxGraphModel dx="1200" dy="800" grid="1" gridSize="10" guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="1" pageScale="1" pageWidth="1600" pageHeight="1200" math="0" shadow="0">
      <root>
        <mxCell id="0" />
        <mxCell id="1" parent="0" />

        <!-- ==================== CAPA MODELO ==================== -->
        <mxCell id="pkg_modelo" value="Paquete com.taller.modelo (Entidades y POO)" style="shape=folder;fontStyle=1;tabWidth=260;tabHeight=30;tabPosition=left;html=1;whiteSpace=wrap;fillColor=#f8f9fa;strokeColor=#3d3f43;" vertex="1" parent="1">
          <mxGeometry x="40" y="40" width="1520" height="380" as="geometry" />
        </mxCell>

        <!-- HERENCIA PERSONA -->
        <mxCell id="c_persona" value="&lt;&lt;Abstract&gt;&gt;&#xa;&lt;b&gt;Persona&lt;/b&gt;&#xa;--&#xa;# id: int&#xa;# nombre: String&#xa;# telefono: String&#xa;# email: String&#xa;--&#xa;+ resumen(): String [abstract]&#xa;+ getId(): int&#xa;+ getNombre(): String" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="40" y="45" width="200" height="150" as="geometry" />
        </mxCell>

        <mxCell id="c_cliente" value="&lt;b&gt;Cliente&lt;/b&gt;&#xa;--&#xa;- direccion: String&#xa;--&#xa;+ resumen(): String&#xa;+ getDireccion(): String" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="30" y="240" width="160" height="110" as="geometry" />
        </mxCell>

        <mxCell id="c_mecanico" value="&lt;b&gt;Mecanico&lt;/b&gt;&#xa;--&#xa;- especialidad: String&#xa;- disponible: boolean&#xa;--&#xa;+ resumen(): String&#xa;+ isDisponible(): boolean" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="210" y="240" width="160" height="110" as="geometry" />
        </mxCell>

        <!-- POLIMORFISMO ITEM PRESUPUESTO -->
        <mxCell id="c_item_base" value="&lt;&lt;Abstract&gt;&gt;&#xa;&lt;b&gt;ItemPresupuesto&lt;/b&gt;&#xa;--&#xa;# id: int&#xa;# ordenId: int&#xa;# tipo: String&#xa;# descripcion: String&#xa;--&#xa;+ calcularSubtotal(): double [abstract]&#xa;+ getDescripcion(): String" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="440" y="45" width="220" height="150" as="geometry" />
        </mxCell>

        <mxCell id="c_item_ref" value="&lt;b&gt;ItemRefaccion&lt;/b&gt;&#xa;--&#xa;- refaccionId: int&#xa;- precioUnitario: double&#xa;- cantidad: int&#xa;--&#xa;+ calcularSubtotal(): double" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="410" y="240" width="180" height="110" as="geometry" />
        </mxCell>

        <mxCell id="c_item_mano" value="&lt;b&gt;ItemManoObra&lt;/b&gt;&#xa;--&#xa;- costoFijo: double&#xa;- horas: double&#xa;--&#xa;+ calcularSubtotal(): double" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="610" y="240" width="180" height="110" as="geometry" />
        </mxCell>

        <!-- USUARIOS Y ROLES -->
        <mxCell id="c_usuario" value="&lt;b&gt;Usuario&lt;/b&gt;&#xa;--&#xa;- id: int&#xa;- username: String&#xa;- claveHash: String&#xa;- rol: RolUsuario&#xa;- personaId: Integer&#xa;--&#xa;+ verificarClave(txt): boolean" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="830" y="45" width="190" height="150" as="geometry" />
        </mxCell>

        <mxCell id="enum_rol" value="&lt;&lt;Enum&gt;&gt;&#xa;&lt;b&gt;RolUsuario&lt;/b&gt;&#xa;--&#xa;+ SUPERADMIN&#xa;+ GERENTE&#xa;+ MECANICO&#xa;+ EMPLEADO&#xa;+ CLIENTE" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="845" y="235" width="160" height="120" as="geometry" />
        </mxCell>

        <!-- VEHICULOS, ORDENES Y REFACCIONES -->
        <mxCell id="c_vehiculo" value="&lt;b&gt;Vehiculo&lt;/b&gt;&#xa;--&#xa;- id: int, placas: String&#xa;- marca, modelo, color: String&#xa;- clienteId: int&#xa;- estatus: EstatusVehiculo&#xa;- rutaFoto: String" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="1050" y="45" width="200" height="140" as="geometry" />
        </mxCell>

        <mxCell id="c_orden" value="&lt;b&gt;OrdenReparacion&lt;/b&gt;&#xa;--&#xa;- id: int, vehiculoId: int&#xa;- mecanicoId: Integer&#xa;- fechaIngreso: LocalDateTime&#xa;- estatus: EstatusVehiculo&#xa;- items: List&lt;ItemPresupuesto&gt;" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="1280" y="45" width="220" height="150" as="geometry" />
        </mxCell>

        <mxCell id="c_refaccion" value="&lt;b&gt;Refaccion&lt;/b&gt;&#xa;--&#xa;- id: int, nombre: String&#xa;- precioUnitario: double&#xa;- stock: int, rutaFoto: String" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="1050" y="235" width="200" height="120" as="geometry" />
        </mxCell>

        <mxCell id="c_bitacora_mod" value="&lt;b&gt;RegistroBitacora&lt;/b&gt;&#xa;--&#xa;- id: int, username: String&#xa;- fechaHora: LocalDateTime&#xa;- accion: String, detalle: String" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="pkg_modelo">
          <mxGeometry x="1280" y="235" width="220" height="120" as="geometry" />
        </mxCell>

        <!-- Herencias Modelo -->
        <mxCell id="h_c" style="endArrow=block;endFill=0;html=1;" edge="1" parent="pkg_modelo" source="c_cliente" target="c_persona"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="h_m" style="endArrow=block;endFill=0;html=1;" edge="1" parent="pkg_modelo" source="c_mecanico" target="c_persona"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="h_ir" style="endArrow=block;endFill=0;html=1;" edge="1" parent="pkg_modelo" source="c_item_ref" target="c_item_base"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="h_im" style="endArrow=block;endFill=0;html=1;" edge="1" parent="pkg_modelo" source="c_item_mano" target="c_item_base"><mxGeometry relative="1" as="geometry" /></mxCell>

        <!-- ==================== CAPA DAO ==================== -->
        <mxCell id="pkg_dao" value="Paquete com.taller.dao (Persistencia MySQL/SQLite)" style="shape=folder;fontStyle=1;tabWidth=260;tabHeight=30;tabPosition=left;html=1;whiteSpace=wrap;fillColor=#f8f9fa;strokeColor=#3d3f43;" vertex="1" parent="1">
          <mxGeometry x="40" y="450" width="1520" height="240" as="geometry" />
        </mxCell>

        <mxCell id="dao_conexion" value="&lt;b&gt;ConexionBD&lt;/b&gt;&#xa;--&#xa;+ getConexion(): Connection&#xa;+ inicializarEsquema(): void&#xa;+ sembrarDatosDefecto(): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#e1d5e7;strokeColor=#9673a6;fontStyle=1;" vertex="1" parent="pkg_dao">
          <mxGeometry x="40" y="55" width="220" height="130" as="geometry" />
        </mxCell>

        <mxCell id="dao_usuario" value="&lt;b&gt;UsuarioDAO&lt;/b&gt;&#xa;--&#xa;+ autenticar(u, c): Usuario&#xa;+ registrar(u, c, r, p): Usuario&#xa;+ existeUsername(u): boolean" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="pkg_dao">
          <mxGeometry x="290" y="55" width="210" height="130" as="geometry" />
        </mxCell>

        <mxCell id="dao_orden" value="&lt;b&gt;OrdenDAO&lt;/b&gt;&#xa;--&#xa;+ crear(o): int&#xa;+ listarPorMecanico(mId): List&#xa;+ listarPorVehiculo(vId): List&#xa;+ actualizarEstatus(id, est): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="pkg_dao">
          <mxGeometry x="530" y="55" width="230" height="140" as="geometry" />
        </mxCell>

        <mxCell id="dao_vehiculo" value="&lt;b&gt;VehiculoDAO&lt;/b&gt;&#xa;--&#xa;+ crear(v): int&#xa;+ eliminar(id): void (con stock)&#xa;+ listarPorCliente(cId): List" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="pkg_dao">
          <mxGeometry x="790" y="55" width="220" height="130" as="geometry" />
        </mxCell>

        <mxCell id="dao_cliente" value="&lt;b&gt;ClienteDAO&lt;/b&gt;&#xa;--&#xa;+ crear(c): int, actualizar(c)&#xa;+ eliminar(id): void (con stock)&#xa;+ listarTodos(): List" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="pkg_dao">
          <mxGeometry x="1040" y="55" width="230" height="130" as="geometry" />
        </mxCell>

        <mxCell id="dao_bitacora" value="&lt;b&gt;BitacoraDAO&lt;/b&gt;&#xa;--&#xa;+ registrar(acc, det): void&#xa;+ listarTodos(): List" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="pkg_dao">
          <mxGeometry x="1300" y="55" width="190" height="130" as="geometry" />
        </mxCell>

        <!-- ==================== CAPA UI ==================== -->
        <mxCell id="pkg_ui" value="Paquete com.taller.ui (Vistas Swing / Dashboard)" style="shape=folder;fontStyle=1;tabWidth=260;tabHeight=30;tabPosition=left;html=1;whiteSpace=wrap;fillColor=#f8f9fa;strokeColor=#3d3f43;" vertex="1" parent="1">
          <mxGeometry x="40" y="720" width="1520" height="420" as="geometry" />
        </mxCell>

        <mxCell id="ui_login" value="&lt;b&gt;LoginFrame&lt;/b&gt;&#xa;--&#xa;- txtUsuario, txtClave&#xa;--&#xa;+ intentarLogin(): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="pkg_ui">
          <mxGeometry x="40" y="55" width="190" height="110" as="geometry" />
        </mxCell>

        <mxCell id="ui_dash" value="&lt;b&gt;MainDashboard&lt;/b&gt;&#xa;--&#xa;- cardLayout: CardLayout&#xa;--&#xa;+ construirMenuLateral()&#xa;+ etiquetaRol(r): String" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#dae8fc;strokeColor=#6c8ebf;fontStyle=1;" vertex="1" parent="pkg_ui">
          <mxGeometry x="260" y="55" width="220" height="110" as="geometry" />
        </mxCell>

        <mxCell id="ui_cliente_vista" value="&lt;b&gt;PanelClienteVista&lt;/b&gt;&#xa;--&#xa;- panelVehiculos: JPanel&#xa;--&#xa;+ cargarDatos(): void&#xa;+ crearTarjetaVehiculo(v)" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="pkg_ui">
          <mxGeometry x="510" y="55" width="220" height="110" as="geometry" />
        </mxCell>

        <mxCell id="ui_estatus" value="&lt;b&gt;PanelEstatus&lt;/b&gt;&#xa;--&#xa;+ refrescar(): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_ui">
          <mxGeometry x="760" y="55" width="170" height="90" as="geometry" />
        </mxCell>

        <mxCell id="ui_ordenes" value="&lt;b&gt;PanelOrdenes&lt;/b&gt;&#xa;--&#xa;+ refrescar(): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_ui">
          <mxGeometry x="950" y="55" width="170" height="90" as="geometry" />
        </mxCell>

        <mxCell id="ui_vehiculos" value="&lt;b&gt;PanelVehiculos&lt;/b&gt;&#xa;--&#xa;+ refrescar(): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_ui">
          <mxGeometry x="1140" y="55" width="170" height="90" as="geometry" />
        </mxCell>

        <mxCell id="ui_personas" value="&lt;b&gt;PanelPersonas&lt;/b&gt;&#xa;--&#xa;+ refrescar(): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="pkg_ui">
          <mxGeometry x="1330" y="55" width="160" height="90" as="geometry" />
        </mxCell>

        <mxCell id="ui_usuarios" value="&lt;b&gt;PanelUsuarios&lt;/b&gt;&#xa;--&#xa;+ crearCuenta(): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="pkg_ui">
          <mxGeometry x="40" y="210" width="190" height="90" as="geometry" />
        </mxCell>

        <mxCell id="ui_refacciones" value="&lt;b&gt;PanelRefacciones&lt;/b&gt;&#xa;--&#xa;+ refrescar(): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_ui">
          <mxGeometry x="260" y="210" width="190" height="90" as="geometry" />
        </mxCell>

        <mxCell id="ui_bitacora" value="&lt;b&gt;PanelBitacora&lt;/b&gt;&#xa;--&#xa;+ refrescar(): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="pkg_ui">
          <mxGeometry x="480" y="210" width="180" height="90" as="geometry" />
        </mxCell>

        <mxCell id="ui_inv_dialog" value="&lt;b&gt;InventarioVisualDialog&lt;/b&gt;&#xa;--&#xa;+ guardarInventario(): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_ui">
          <mxGeometry x="690" y="210" width="210" height="90" as="geometry" />
        </mxCell>

        <mxCell id="ui_refrescable" value="&lt;&lt;Interface&gt;&gt;&#xa;&lt;b&gt;Refrescable&lt;/b&gt;&#xa;--&#xa;+ refrescar(): void" style="swipe;html=1;whiteSpace=wrap;align=center;verticalAlign=top;fillColor=#f5f5f5;strokeColor=#666666;" vertex="1" parent="pkg_ui">
          <mxGeometry x="930" y="210" width="160" height="90" as="geometry" />
        </mxCell>

        <!-- Relaciones UI -> DAO -->
        <mxCell id="rel_ui_dao" value="Utilizan DAOs para persistencia" style="endArrow=open;endFill=0;html=1;dashed=1;" edge="1" parent="1" source="pkg_ui" target="pkg_dao"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="rel_dao_mod" value="Mapean y manipulan objetos" style="endArrow=open;endFill=0;html=1;dashed=1;" edge="1" parent="1" source="pkg_dao" target="pkg_modelo"><mxGeometry relative="1" as="geometry" /></mxCell>
      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
```

---

## 3. Diagrama de Componentes y Módulos Completo (Arquitectura en 3 Capas)

Muestra los **módulos de software reales**, las **tablas de la base de datos MySQL/SQLite** y la **comunicación interna entre paneles Swing, DAOs y la Bitácora de Auditoría**.

```xml
<mxfile host="app.diagrams.net">
  <diagram name="Componentes Completo - Arquitectura Hotwheels Tam">
    <mxGraphModel dx="1200" dy="800" grid="1" gridSize="10" guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="1" pageScale="1" pageWidth="1500" pageHeight="1100" math="0" shadow="0">
      <root>
        <mxCell id="0" />
        <mxCell id="1" parent="0" />

        <!-- CAPA GUI / VISTAS -->
        <mxCell id="pkg_gui" value="Capa de Presentación / Interfaz de Usuario (Swing GUI)" style="shape=folder;fontStyle=1;tabWidth=320;tabHeight=30;tabPosition=left;html=1;whiteSpace=wrap;fillColor=#f8f9fa;strokeColor=#3d3f43;" vertex="1" parent="1">
          <mxGeometry x="40" y="40" width="1420" height="230" as="geometry" />
        </mxCell>

        <mxCell id="cmp_login" value="[Componente]&#xa;LoginFrame&#xa;(Autenticación y Seguridad)" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#dae8fc;strokeColor=#6c8ebf;fontStyle=1;" vertex="1" parent="pkg_gui">
          <mxGeometry x="40" y="50" width="210" height="70" as="geometry" />
        </mxCell>

        <mxCell id="cmp_dash" value="[Componente Contenedor]&#xa;MainDashboard&#xa;(Navegación Dinámica por Roles)" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#dae8fc;strokeColor=#6c8ebf;fontStyle=1;" vertex="1" parent="pkg_gui">
          <mxGeometry x="280" y="50" width="250" height="70" as="geometry" />
        </mxCell>

        <mxCell id="cmp_cliente_vista" value="[Componente Exclusivo]&#xa;PanelClienteVista&#xa;(Solo Lectura para Rol Cliente)" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#e1d5e7;strokeColor=#9673a6;fontStyle=1;" vertex="1" parent="pkg_gui">
          <mxGeometry x="560" y="50" width="240" height="70" as="geometry" />
        </mxCell>

        <mxCell id="cmp_modulos_operativos" value="[Módulos Operativos]&#xa;PanelEstatus | PanelVehiculos&#xa;PanelOrdenes | PanelRefacciones" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_gui">
          <mxGeometry x="830" y="50" width="280" height="70" as="geometry" />
        </mxCell>

        <mxCell id="cmp_modulos_admin" value="[Módulos Administrativos]&#xa;PanelUsuarios | PanelPersonas&#xa;PanelBitacora (Auditoría)" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="pkg_gui">
          <mxGeometry x="1140" y="50" width="260" height="70" as="geometry" />
        </mxCell>

        <mxCell id="cmp_inv_dialog" value="[Componente]&#xa;InventarioVisualDialog&#xa;(Inspección Gráfica del Carro)" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_gui">
          <mxGeometry x="830" y="140" width="280" height="60" as="geometry" />
        </mxCell>

        <!-- CAPA LOGICA DE NEGOCIO Y DAOs -->
        <mxCell id="pkg_logica" value="Capa de Lógica de Negocio, Seguridad y DAOs (Acceso a Datos)" style="shape=folder;fontStyle=1;tabWidth=340;tabHeight=30;tabPosition=left;html=1;whiteSpace=wrap;fillColor=#f8f9fa;strokeColor=#3d3f43;" vertex="1" parent="1">
          <mxGeometry x="40" y="310" width="1420" height="230" as="geometry" />
        </mxCell>

        <mxCell id="cmp_seguridad" value="[Módulo Seguridad]&#xa;SeguridadUtil&#xa;(Hashing SHA-256 / Regex Email)" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="pkg_logica">
          <mxGeometry x="50" y="50" width="240" height="70" as="geometry" />
        </mxCell>

        <mxCell id="cmp_sesion" value="[Módulo Sesión]&#xa;Sesion Singleton&#xa;(Gestión del Usuario Actual)" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#dae8fc;strokeColor=#6c8ebf;" vertex="1" parent="pkg_logica">
          <mxGeometry x="320" y="50" width="220" height="70" as="geometry" />
        </mxCell>

        <mxCell id="cmp_daos_operativos" value="[Componentes DAO Operativos]&#xa;VehiculoDAO | OrdenDAO | RefaccionDAO&#xa;(Sincronización de Estatus y Stock)" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="pkg_logica">
          <mxGeometry x="570" y="50" width="310" height="70" as="geometry" />
        </mxCell>

        <mxCell id="cmp_daos_admin" value="[Componentes DAO Admin]&#xa;UsuarioDAO | ClienteDAO | MecanicoDAO&#xa;(Control de Roles e Integridad Referencial)" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="pkg_logica">
          <mxGeometry x="910" y="50" width="320" height="70" as="geometry" />
        </mxCell>

        <mxCell id="cmp_bitacora_dao" value="[Módulo Transversal Auditoría]&#xa;BitacoraDAO&#xa;(Registra todas las acciones del sistema)" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#fff2cc;strokeColor=#d6b656;fontStyle=1;" vertex="1" parent="pkg_logica">
          <mxGeometry x="570" y="140" width="660" height="60" as="geometry" />
        </mxCell>

        <!-- CAPA DE BASE DE DATOS Y CONEXION -->
        <mxCell id="pkg_datos" value="Capa de Persistencia y Base de Datos (ConexionBD JDBC)" style="shape=folder;fontStyle=1;tabWidth=340;tabHeight=30;tabPosition=left;html=1;whiteSpace=wrap;fillColor=#f8f9fa;strokeColor=#3d3f43;" vertex="1" parent="1">
          <mxGeometry x="40" y="580" width="1420" height="250" as="geometry" />
        </mxCell>

        <mxCell id="cmp_conexion_jdbc" value="[Componente Conector]&#xa;ConexionBD (Driver JDBC MySQL / SQLite)&#xa;Auto-inicialización de Esquema y Datos" style="html=1;dropTarget=0;whiteSpace=wrap;fillColor=#e1d5e7;strokeColor=#9673a6;fontStyle=1;" vertex="1" parent="pkg_datos">
          <mxGeometry x="60" y="50" width="340" height="150" as="geometry" />
        </mxCell>

        <mxCell id="db_mysql" value="Base de Datos Relacional (hotwheels_tam)&#xa;--&#xa;+ usuarios&#xa;+ clientes&#xa;+ mecanicos&#xa;+ vehiculos (ON DELETE SET NULL)&#xa;+ ordenes (ON DELETE CASCADE)&#xa;+ items_presupuesto (ON DELETE CASCADE)&#xa;+ refacciones&#xa;+ bitacora (Tabla Auditoría)" style="shape=cylinder3;whiteSpace=wrap;html=1;boundedLbl=1;backgroundOutline=1;size=15;fillColor=#e1d5e7;strokeColor=#9673a6;fontStyle=1;" vertex="1" parent="pkg_datos">
          <mxGeometry x="480" y="40" width="880" height="170" as="geometry" />
        </mxCell>

        <!-- CONEXIONES -->
        <mxCell id="c_gui_log" style="endArrow=open;endSize=12;dashed=1;html=1;strokeWidth=2;" edge="1" parent="1" source="pkg_gui" target="pkg_logica"><mxGeometry relative="1" as="geometry" /></mxCell>
        <mxCell id="c_log_dat" style="endArrow=open;endSize=12;dashed=1;html=1;strokeWidth=2;" edge="1" parent="1" source="pkg_logica" target="pkg_datos"><mxGeometry relative="1" as="geometry" /></mxCell>
      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
```
