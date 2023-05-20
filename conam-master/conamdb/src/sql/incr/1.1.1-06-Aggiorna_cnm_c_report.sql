update cnm_c_report set jasper = null where cod_report = 'BOLORDSOL';

update cnm_c_report set jrxml = '<!-- Created with Jaspersoft Studio version 6.2.1.final using JasperReports Library version 6.2.1  -->
<!-- 2019-08-06T08:55:40 -->
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="Bollettino-Postale-1x" pageWidth="595" pageHeight="842" columnWidth="555" leftMargin="20" rightMargin="20" topMargin="20" bottomMargin="20" uuid="3a198f7c-26e6-4466-8e16-f6ebefcfa2ac">
	<property name="com.jaspersoft.studio.data.defaultdataadapter" value="One Empty Record"/>
	<subDataset name="listabollettino" uuid="3c1341e8-28f8-4f2d-8c70-50834327e92a">
		<parameter name="bollettinoSollecito" class="java.awt.image.BufferedImage"/>
		<queryString>
			<![CDATA[]]>
		</queryString>
		<field name="dataScadenzaRata1" class="java.time.LocalDate">
			<fieldDescription><![CDATA[dataScadenzaRata1]]></fieldDescription>
		</field>
		<field name="numRata1" class="java.math.BigDecimal">
			<fieldDescription><![CDATA[numRata1]]></fieldDescription>
		</field>
		<field name="enteCreditore" class="java.lang.String">
			<fieldDescription><![CDATA[enteCreditore]]></fieldDescription>
		</field>
		<field name="settoreEnte" class="java.lang.String">
			<fieldDescription><![CDATA[settoreEnte]]></fieldDescription>
		</field>
		<field name="cbill" class="java.lang.String">
			<fieldDescription><![CDATA[cbill]]></fieldDescription>
		</field>
		<field name="oggettoPagamento" class="java.lang.String">
			<fieldDescription><![CDATA[oggettoPagamento]]></fieldDescription>
		</field>
		<field name="dataMatrix1" class="java.awt.image.BufferedImage">
			<fieldDescription><![CDATA[dataMatrix1]]></fieldDescription>
		</field>
		<field name="codiceAvvisoRata1" class="java.lang.String">
			<fieldDescription><![CDATA[codiceAvvisoRata1]]></fieldDescription>
		</field>
		<field name="numeroContoPostale" class="java.lang.String">
			<fieldDescription><![CDATA[numeroContoPostale]]></fieldDescription>
		</field>
		<field name="autorizzazione" class="java.lang.String">
			<fieldDescription><![CDATA[autorizzazione]]></fieldDescription>
		</field>
		<field name="qrcode1" class="java.awt.image.BufferedImage">
			<fieldDescription><![CDATA[qrcode1]]></fieldDescription>
		</field>
		<field name="denominazioneSoggetto" class="java.lang.String">
			<fieldDescription><![CDATA[denominazioneSoggetto]]></fieldDescription>
		</field>
		<field name="cfEnteCreditore" class="java.lang.String">
			<fieldDescription><![CDATA[cfEnteCreditore]]></fieldDescription>
		</field>
		<field name="importoRata1" class="java.math.BigDecimal">
			<fieldDescription><![CDATA[importoRata1]]></fieldDescription>
		</field>
		<field name="cfEnteDebitore" class="java.lang.String">
			<fieldDescription><![CDATA[cfEnteDebitore]]></fieldDescription>
		</field>
		<field name="intestatarioContoCorrentePostale" class="java.lang.String">
			<fieldDescription><![CDATA[intestatarioContoCorrentePostale]]></fieldDescription>
		</field>
		<field name="infoEnte" class="java.lang.String">
			<fieldDescription><![CDATA[infoEnte]]></fieldDescription>
		</field>
		<field name="indirizzoEnteDebitore" class="java.lang.String">
			<fieldDescription><![CDATA[indirizzoEnteDebitore]]></fieldDescription>
		</field>
		<field name="comuneEnteDebitore" class="java.lang.String">
			<fieldDescription><![CDATA[comuneEnteDebitore]]></fieldDescription>
		</field>
	</subDataset>
	<parameter name="listaBollettiniJasper" class="net.sf.jasperreports.engine.data.JRBeanCollectionDataSource" isForPrompting="false">
		<parameterDescription><![CDATA[]]></parameterDescription>
	</parameter>
	<parameter name="bollettinoSollecito" class="java.awt.image.BufferedImage">
		<parameterDescription><![CDATA[]]></parameterDescription>
	</parameter>
	<queryString>
		<![CDATA[select 1 from dual]]>
	</queryString>
	<detail>
		<band height="802">
			<componentElement>
				<reportElement stretchType="RelativeToBandHeight" x="0" y="0" width="555" height="802" uuid="601659dc-4f5f-4167-8cd4-417785b6f685"/>
				<jr:list xmlns:jr="http://jasperreports.sourceforge.net/jasperreports/components" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd" printOrder="Vertical">
					<datasetRun subDataset="listabollettino" uuid="3aae0eb8-b5a7-476e-9086-ef6f28e000c8">
						<datasetParameter name="bollettinoSollecito">
							<datasetParameterExpression><![CDATA[$P{bollettinoSollecito}]]></datasetParameterExpression>
						</datasetParameter>
						<dataSourceExpression><![CDATA[$P{listaBollettiniJasper}]]></dataSourceExpression>
					</datasetRun>
					<jr:listContents height="802" width="555">
						<image>
							<reportElement x="0" y="0" width="555" height="802" uuid="22f9e186-594e-49c6-af90-aa7203152da0"/>
							<imageExpression><![CDATA[$P{bollettinoSollecito}]]></imageExpression>
						</image>
						<textField>
							<reportElement x="30" y="85" width="340" height="30" uuid="ae7c4b43-faf2-47c5-b596-08b76bc886cc"/>
							<textElement verticalAlignment="Top">
								<font fontName="SansSerif" size="13" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{oggettoPagamento}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="152" y="139" width="120" height="13" uuid="02939e6d-42b2-4cb7-88ca-55a8d24aff90">
								<property name="com.jaspersoft.studio.unit.x" value="pixel"/>
							</reportElement>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{cfEnteCreditore}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="29" y="710" width="130" height="20" forecolor="#BAB6B6" uuid="ac3112f7-4d67-4e07-b22a-0b8c07018f54"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font size="8" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{autorizzazione}]]></textFieldExpression>
						</textField>
						<image scaleImage="FillFrame" hAlign="Center" vAlign="Middle">
							<reportElement positionType="Float" x="454" y="652" width="70" height="70" uuid="9e9343ee-ece5-4c8d-829c-4c2fa7735d1a">
								<property name="com.jaspersoft.studio.unit.width" value="pixel"/>
							</reportElement>
							<imageExpression><![CDATA[$F{dataMatrix1}]]></imageExpression>
						</image>
						<image scaleImage="FillFrame" hAlign="Center" vAlign="Middle">
							<reportElement x="165" y="470" width="70" height="70" uuid="22992b65-3661-4603-821e-624484fcbb95"/>
							<imageExpression><![CDATA[$F{qrcode1}]]></imageExpression>
						</image>
						<textField>
							<reportElement x="248" y="525" width="52" height="20" uuid="b5f8441a-67b4-4904-9b2a-b41a3ad111c5">
								<property name="com.jaspersoft.studio.unit.x" value="pixel"/>
							</reportElement>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{cbill}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="301" y="525" width="133" height="20" uuid="4b76af5f-6665-4d6e-addd-169dcc106675"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{codiceAvvisoRata1}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="440" y="525" width="84" height="20" uuid="4c97c20e-3323-4c19-80ce-43a76e7b238d">
								<property name="com.jaspersoft.studio.unit.x" value="pixel"/>
							</reportElement>
							<textElement textAlignment="Right" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{cfEnteCreditore}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="298" y="482" width="182" height="13" uuid="31ace9ca-5c8b-4d67-be51-cf390d23e55c"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font size="8" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{enteCreditore}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="290" y="460" width="153" height="20" uuid="31fcd1a3-e995-4692-b836-980e78b99149"/>
							<textElement textAlignment="Left" verticalAlignment="Bottom">
								<font size="8" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{denominazioneSoggetto}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="460" y="469" width="90" height="13" uuid="618e4492-b867-4334-ab4c-57648b2be2ec"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[new java.text.DecimalFormat("#,##0.00").format($F{importoRata1})]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="168" y="708" width="133" height="15" uuid="56398f71-3a1c-4c63-884e-f5d51e8e40b3"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{codiceAvvisoRata1}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="324" y="708" width="99" height="15" uuid="1953c9fb-0b26-4f20-afa8-4161bd12173c">
								<property name="com.jaspersoft.studio.unit.x" value="pixel"/>
							</reportElement>
							<textElement textAlignment="Right" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{cfEnteCreditore}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="460" y="614" width="90" height="13" uuid="5109e94d-a3d7-46f6-8c82-fa37ee66aef4"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[new java.text.DecimalFormat("#,##0.00").format($F{importoRata1})]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="231" y="614" width="140" height="13" uuid="abe54b77-48a9-4b2e-a39a-b0c094032f92"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{numeroContoPostale}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="24" y="303" width="80" height="20" uuid="5b9c1de9-5fcf-4b75-9654-6d291b31f15a"/>
							<textElement textAlignment="Right" verticalAlignment="Middle">
								<font size="12" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[new java.text.DecimalFormat("#,##0.00").format($F{importoRata1})]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="235" y="679" width="200" height="20" uuid="f80f053e-42ff-4f67-8c01-eb53a423eebf"/>
							<textElement textAlignment="Left" verticalAlignment="Top">
								<font size="8" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{oggettoPagamento}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="204" y="640" width="231" height="15" uuid="f5218bf4-1bb2-45f6-a948-f1c8a64786ae"/>
							<textElement textAlignment="Left" verticalAlignment="Bottom">
								<font size="8" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{intestatarioContoCorrentePostale}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="211" y="657" width="224" height="15" uuid="44fb75c9-7419-471a-842b-717d74763b01"/>
							<textElement textAlignment="Left" verticalAlignment="Bottom">
								<font size="8" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{denominazioneSoggetto}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="298" y="160" width="242" height="30" uuid="d5d89574-a17f-478e-9d4b-a89fcc0ee259"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{denominazioneSoggetto}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="443" y="139" width="112" height="13" uuid="55d967b9-160f-4826-9855-53241421cba8"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{cfEnteDebitore}]]></textFieldExpression>
						</textField>
						<textField isBlankWhenNull="true">
							<reportElement positionType="Float" x="298" y="190" width="242" height="30" isRemoveLineWhenBlank="true" uuid="cbbf14c5-5ff4-4afa-b406-cc4b057a361d"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{indirizzoEnteDebitore}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="30" y="225" width="250" height="35" uuid="46fbbbde-8676-45c5-9741-a5625eb82114">
								<property name="com.jaspersoft.studio.unit.x" value="pixel"/>
							</reportElement>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{infoEnte}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="30" y="187" width="250" height="35" uuid="10f67500-1afa-4c0e-99b5-1ffec11142ce">
								<property name="com.jaspersoft.studio.unit.x" value="pixel"/>
							</reportElement>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{settoreEnte}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="30" y="165" width="250" height="20" uuid="7d2fbfef-816e-4b49-97f3-86c6f1997a12">
								<property name="com.jaspersoft.studio.unit.x" value="pixel"/>
							</reportElement>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{enteCreditore}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="474" y="430" width="80" height="20" uuid="f5b09882-3739-4e51-9a90-bccb28f77f67"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font size="12" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{dataScadenzaRata1}.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="474" y="561" width="80" height="20" uuid="bbb7e5fe-1787-4016-bf37-6edc52bdb024"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font size="12" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{dataScadenzaRata1}.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="168" y="303" width="80" height="20" uuid="d2cbcce3-58fc-4843-86da-54514549cc74"/>
							<textElement textAlignment="Left" verticalAlignment="Middle">
								<font size="12" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{dataScadenzaRata1}.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))]]></textFieldExpression>
						</textField>
						<textField isBlankWhenNull="true">
							<reportElement x="298" y="222" width="242" height="50" isRemoveLineWhenBlank="true" isPrintWhenDetailOverflows="true" uuid="b6aa5a74-fdc3-40c7-a5f5-f393edcd41c1"/>
							<textElement textAlignment="Left" verticalAlignment="Top">
								<font isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{comuneEnteDebitore}]]></textFieldExpression>
						</textField>
						<textField>
							<reportElement x="324" y="497" width="220" height="20" uuid="a2e0ccfb-bc5c-4842-8825-23d392e20d61"/>
							<textElement textAlignment="Left" verticalAlignment="Top">
								<font size="8" isBold="true"/>
							</textElement>
							<textFieldExpression><![CDATA[$F{oggettoPagamento}]]></textFieldExpression>
						</textField>
					</jr:listContents>
				</jr:list>
			</componentElement>
		</band>
	</detail>
</jasperReport>'
where cod_report = 'BOLORDSOL';