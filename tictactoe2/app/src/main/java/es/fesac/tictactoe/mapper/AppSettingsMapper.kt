package es.fesac.tictactoe.mapper

import es.fesac.tictactoe.model.AppSettingsBo
import es.fesac.tictactoe.vo.AppSettingsVo

fun AppSettingsBo.toVo() : AppSettingsVo {
    return AppSettingsVo(
        homeImgUrl = homeImgUrl,
        multiplayerEnable = multiplayerEnable
    )
}